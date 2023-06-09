# Modified_Necklace_Pipeline

The existing Necklace pipeline https://github.com/Oshlack/necklace constructs superTranscripts by combining reference guided and de novo transcriptome assembly. Which is quite time and resource consuming approach when applied to large number of transcriptomics datasets, since all stages are parallely running, although it produces ouputs from superTranscripts assembly to expression counts. In present investigation we maked modified version of the same and splitted the assembly steps into three parts namely reference guided assembly, de novo transcriptome assembly and clustering this will yield final superTranscripts by using Lace. If one wants to go for gene expression count, can perform it immidiately after the reference guided and de novo transcriptome assembly.

# Downloading pipeline 
To clone Modified_Necklace_Pipeline to your local environment, You can use 'git' command as follows:
```
git clone https://github.com/nikhilshinde0909/Modified_Necklace_Pipeline.git
```
# Installing softwares
For software installation you can install softwares manually or with conda as follows

eg.
```
conda install -c bioconda trinity
```
or 

You can also create and activate conda environment for executing pipeline as follows 
```
conda env create -f environment.yml
conda activate mod-necklace
```
once all softwares are installed you need to add path for the same in file called tools.groovy

eg.
bpipe="/opt/data/home/nikhil/anaconda3/bin/bpipe"

the other tools such as chimera braker, make blocks, gtf2flatgtf etc. will be available as a pre-compiled bineries in the directory {Path to mod necklace}/tools/bin/

You need to add the path for the same in file called tools.groovy 

eg. \
remove_clusters_match="/opt/data/home/nikhil/mod_necklace/tools/bin/remove_clusters_match"


# De novo assembling the transcripts from large number of RNASeq datasets
For de novo assembling transcripts from large number of RNASeq datasets we have script called 'denovo.groovy' which is further depends on subscript 'de_novo_assembly.groovy' under bpipe_stages folder. The present pipeline also automated with bpipe similarly like necklace.

eg.
```
{path_to_bpipe}/bpipe run -n 16 ~/mod_necklace/denovo.groovy data/data.txt
```
# Reference guided transcripts assembly RNASeq datasets
For reference guided transcript assembly, we have script called 'genome_guided.groovy' which is further depends on subscript 'genome_guided_assembly.groovy' and 'build_genome_superTranscriptome.groovy' under bpipe_stages folder. 

eg.
```
{path_to_bpipe}/bpipe run -n 16 ~/mod_necklace/genome_guided.groovy data/data.txt
```
# Final superTranscript construction 
For constructing superTranscripts by integrating above two different approaches we have to copy files 'de_novo_assembly.fasta' and 'genome_superT.fasta' in working directory. then you can run script called 'cluster+lace.groovy' to construct superTranscripts as follows:

eg.
```
{path_to_bpipe}/bpipe run -n 16 ~/mod_necklace/cluster+lace.groovy data/data.txt
```
Note:
The path for 'proteins_related_species' would be same as in 'data.txt' file if you are not changing working directory or one can give full path for the same as follows to avoid error

eg. \
proteins_related_species="/opt/data/home/nikhil/superTranscriptome/data/Sorghum_bicolor_all.pep.fa"

# Final superTranscript construction along with expression counts
The superTranscripts will be constructs by integrating above two assembly approaches we have to copy files 'de_novo_assembly.fasta' and 'genome_superT.fasta' in working directory. then you need to run script called 'cluster+lace+featureCounts.groovy' which will produce output for superTranscvripts and expression counts as follows:

eg. 
```
{path_to_bpipe}/bpipe run -n 16 ~/mod_necklace/cluster+lace+featureCounts.groovy data/data.txt
```
