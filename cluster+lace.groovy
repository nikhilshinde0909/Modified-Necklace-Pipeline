/***********************************************************
 ** Author: Nikhil Shinde <sd1172@srmist.edu.in>
 ** Last Update: 2021/08/07
 *********************************************************/

VERSION="1.11"

//option strings to pass to tools

blat_options="-minScore=200 -minIdentity=70 -threads="
blat_related_options="-t=dnax -q=prot -minScore=200 -maxIntron=0 -threads="
scaffold_genes=false
de_novo_assembly_file=""


load args[0]

codeBase = file(bpipe.Config.config.script).parentFile.absolutePath
load codeBase+"/tools.groovy"

load codeBase+"/bpipe_stages/cluster.groovy"
load codeBase+"/bpipe_stages/run_lace.groovy"

/******************* Here are the pipeline stages **********************/


run_check = {
    doc "check that the data files exist"
    produce("checks_passed") {
        exec """
            echo "Running necklace version $VERSION" ;
	    echo "Using ${bpipe.Config.config.maxThreads} threads" ;
            echo "Checking for the data files..." ;
	    for i in $proteins_related_species ; 
                 do ls $i 2>/dev/null || { echo "CAN'T FIND ${i}..." ;
		 echo "PLEASE FIX PATH... STOPPING NOW" ; exit 1  ; } ; 
	    done ;
            echo "All looking good" ;
            echo "running  necklace version $VERSION.. checks passed" > $output
        ""","checks"
    }
}

nthreads=bpipe.Config.config.maxThreads

run {run_check + 
     cluster_files +
    run_lace.using(threads: nthreads) }


