# Modified_Necklace_Pipeline

The existing Necklace pipeline https://github.com/Oshlack/necklace constructs superTranscriptoms by combining reference guided and de novo transcriptome assembly. Which has been splitted to construct induvidual reference guided assembly and de novo transcriptome assembly for proccessing large number of RNA-Seq datasets(275) in sorghum. The assemblies from above two different approaches were used to cluster the transcripts and finally to construct super transcripts by using Lace.

# Downloading pipeline 
To clone Modified_Necklace_Pipeline to your local environment, You can use 'git' command as follows:

git clone https://github.com/nikhilshinde0909/Modified_Necklace_Pipeline.git

# Installing softwares
For software installation you can install softwares manually or with conda as follows

eg.\
conda install -c bioconda trinity

or 

You can also create conda environment for executing pipeline as follows 

conda env create -f environment.yml

once all softwares are installed you need to add path for the same in file called tools.groovy

eg.\
bpipe="/opt/data/home/nikhil/anaconda3/bin/bpipe"

the other tools such as chimera braker, make blocks, gtf2flatgtf etc. will be available as a pre-compiled bineries in the directory {Path to mod necklace}/tools/bin/

You need to add the path for the same in file called tools.groovy 

eg.\
remove_clusters_match="/opt/data/home/nikhil/mod_necklace/tools/bin/remove_clusters_match"


# De novo assembling the transcripts from large number of RNASeq datasets
For de novo assembling transcripts from large number of RNASeq datasets we have script called 'denovo.groovy' which is further depends on subscript 'de_novo_assembly.groovy' under bpipe_stages folder. The present pipeline also automated with bpipe similarly like necklace.

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


