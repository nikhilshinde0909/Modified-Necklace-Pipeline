/***********************************************************
 ** Author: Nikhil Shinde <sd1172@srmist.edu.in>
 ** Last Update: 05/12/2021
 *********************************************************/

VERSION="1.12"

//option strings to pass to tools
trinity_options="--max_memory 50G --normalize_reads --no_version_check --trimmomatic"
hisat2_options=""
stringtie_options=""
stringtie_merge_options=""
blat_options="-minScore=200 -minIdentity=98 -threads="
blat_related_options="-t=dnax -q=prot -minScore=200 -maxIntron=0 -threads="
de_novo_assembly_file=""

scaffold_genes=false

fastqFormatPaired="%_*.gz"
fastqFormatSingle="%.gz"

load args[0]

fastqInputFormat=fastqFormatPaired
if(reads_R2=="") fastqInputFormat=fastqFormatSingle

codeBase = file(bpipe.Config.config.script).parentFile.absolutePath
load codeBase+"/tools.groovy"

load codeBase+"/bpipe_stages/genome_guided_assembly.groovy"
load codeBase+"/bpipe_stages/build_genome_superTranscriptome.groovy"
load codeBase+"/bpipe_stages/de_novo_assembly.groovy"
load codeBase+"/bpipe_stages/cluster.groovy"
load codeBase+"/bpipe_stages/run_lace.groovy"



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
	    for i in $genome $annotation $proteins_related_species $inputs.gz ; 
                 do ls $i 2>/dev/null || { echo "CAN'T FIND ${i}..." ;
		 echo "PLEASE FIX PATH... STOPPING NOW" ; exit 1  ; } ; 
	    done ;
            echo "All looking good" ;
            echo "running  necklace version $VERSION.. checks passed" > $output
        ""","checks"
    }
}

nthreads=bpipe.Config.config.maxThreads

run { set_input + run_check + //single thread 
    [ build_genome_guided_assembly + build_genome_superTranscriptome, 
    de_novo_assemble.using(threads: nthreads-1 ) ] + // -2), 
    cluster_files +
    run_lace.using(threads: nthreads)}



