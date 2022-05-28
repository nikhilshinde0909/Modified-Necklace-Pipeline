# Modified_Necklace_Pipeline

The existing Necklace pipeline https://github.com/Oshlack/necklace constructs superTranscriptoms by combining reference guided and de novo transcriptome assembly. Which has been splitted to construct induvidual reference guided assembly and de novo transcriptome assembly for proccessing large number of RNASeq datasets(108) in sorghum. The assemblies from above two different approaches were used to cluster the transcripts and finally to construct super transcripts by using Lace.

# Downloading pipeline 
To make a local copy of a Modified_Necklace_Pipeline, You can use 'git' command as follows:\

git clone https://github.com/sd1172/Modified_Necklace_Pipeline.git

# Installing softwares
For installing softwares needed for running present pipeline one can install softwares with Anaconda by using conda command in current conda environment as follows

eg.\
conda install -c bioconda trinity\
conda install -c bioconda bpipe\
conda install -c bioconda stringtie\
conda install -c bioconda hisat2\
conda install -c bioconda bowtie\
conda install -c bioconda bowtie2\
conda install -c bioconda samtools\
conda install -c bioconda gffread\
conda install -c bioconda lace\
conda install -c bioconda pblat\
conda install -c bioconda blat


or 
One can create new conda environment for running pipeline as follows 

conda create env -n necklace -c bioconda lace

and install the rest of the softwares one by one  as given above

the other tools used by pipeline such as chimera braker, make blocks, gtf2flatgtf etc. will be present in form of pre-compiled bineries and will be available in directory {Path to mod necklace}/tools/bin/

Finally you have to edit the path for installed softwares in file {mod_necklace}/tools.groovy 

eg.\
bpipe="/opt/data/home/nikhil/anaconda3/bin/bpipe"\
hisat2="/opt/data/home/nikhil/anaconda3/bin/hisat2"\
remove_clusters_match="/opt/data/home/nikhil/mod_necklace/tools/bin/remove_clusters_match"


# De novo assembling the transcripts from large number of RNASeq datasets
For de novo assembling transcripts from large number of RNASeq datasets we have script called 'denovo.groovy' which is further depends on subscript 'de_novo_assembly.groovy' under bpipe_stages folder. However like a necklace the present pipeline also automated with bpipe.

eg.\
{path_to_bpipe}/bpipe run -n 16 ~/mod_necklace/denovo.groovy data/data.txt

# Reference guided transcripts assembly RNASeq datasets
For reference guided transcript assembly, we have script called 'genome_guided.groovy' which is further depends on subscript 'genome_guided_assembly.groovy' and 'build_genome_superTranscriptome.groovy' under bpipe_stages folder. 

eg.\
{path_to_bpipe}/bpipe run -n 16 ~/mod_necklace/genome_guided.groovy data/data.txt

# Final superTranscript construction 
For constructing superTranscripts by integrating above two different approaches we have to copy files 'de_novo_assembly.fasta' and 'genome_superT.fasta' in working directory. then you can run script called 'cluster+lace.groovy' to construct superTranscripts as follows:

eg.\
{path_to_bpipe}/bpipe run -n 16 ~/mod_necklace/cluster+lace.groovy data/data.txt

Note:
The path for 'proteins_related_species' would be same as in 'data.txt' file if you are not changing working directory or one can give full path for the same as follows to avoid error

eg.\
proteins_related_species="/opt/data/home/nikhil/superTranscriptome/data/Sorghum_bicolor_all.pep.fa"


