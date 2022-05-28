/***********************************************************
 ** Author: Nikhil Shinde <sd1172@srmist.edu.in>
 ** Last Update: 05/12/2021
 *********************************************************/

VERSION="1.11"

//option strings to pass to tools
trinity_options="--max_memory 100G --normalize_reads --no_version_check --trimmomatic"
de_novo_assembly_file=""
scaffold_genes=false

fastqFormatPaired="%_*.gz"
fastqFormatSingle="%.gz"

load args[0]

fastqInputFormat=fastqFormatPaired
if(reads_R2=="") fastqInputFormat=fastqFormatSingle

codeBase = file(bpipe.Config.config.script).parentFile.absolutePath
load codeBase+"/tools.groovy"

load codeBase+"/bpipe_stages/de_novo_assembly.groovy"

/******************* Here are the pipeline stages **********************/

set_input = {
   def files=reads_R1.split(",")
   if(reads_R2!="") files+=reads_R2.split(",")
   forward files
}

run_check = {
    doc "check that the data files exist"
    produce("checks_passed") {
        exec """
            echo "Running necklace version $VERSION" ;
	    echo "Using ${bpipe.Config.config.maxThreads} threads" ;
            echo "Checking for the data files..." ;
	    for i in $inputs.gz ; 
                 do ls $i 2>/dev/null || { echo "CAN'T FIND ${i}..." ;
		 echo "PLEASE FIX PATH... STOPPING NOW" ; exit 1  ; } ; 
	    done ;
            echo "All looking good" ;
            echo "running  necklace version $VERSION.. checks passed" > $output
        ""","checks"
    }
}

nthreads=bpipe.Config.config.maxThreads

run { set_input + run_check + 
             de_novo_assemble.using(threads: nthreads-1 )} 
