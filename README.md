# benchmarks

Benchmarks for genomics libraries on Apache Spark. Apache 2 licensed.


## Use cases

#### BAM file benchmarks

Test files:
```
s3://giab/technical/platinum_genomes/bams_vcfs_4individules_2013/NA12878_S1_200x.bam
s3://giab/technical/platinum_genomes/bams_vcfs_4individules_2013/NA12878_S1_200x.bam.bai

s3://giab/data/NA12878/NIST_NA12878_HG001_HiSeq_300x/NHGRI_Illumina300X_novoalign_bams/HG001.GRCh38_full_plus_hs38d1_analysis_set_minus_alts.300x.bam
s3://giab/data/NA12878/NIST_NA12878_HG001_HiSeq_300x/NHGRI_Illumina300X_novoalign_bams/HG001.GRCh38_full_plus_hs38d1_analysis_set_minus_alts.300x.bam.bai

s3://1000genomes/1000G_2504_high_coverage/data/ERR3239334/NA12878.final.cram
s3://1000genomes/1000G_2504_high_coverage/data/ERR3239334/NA12878.final.cram.crai
```

1. Count the number of records in a BAM file.
2. Count the number of records in a BAM file matching a filter, i.e.
3. Count the number of records in an indexed BAM file matching a range filter, i.e.
4. Read the header and all records from a BAM file and write them out to a new BAM file.
5. Convert the header and all records from a BAM file per [bdg-formats schema](https://github.com/bigdatagenomics/bdg-formats) and write to Apache Parquet format.

#### Alignments in Apache Parquet format benchmarks

Test files:

```bash
$ adam-submit transformAlignments sample.bam sample.alignments.adam
$ adam-submit transformAlignments -partition_by_start_pos sample.bam sample.alignments.1m.adam
```

1. Count the number of alignment records from a directory in Apache Parquet format.
2. Count the number of alignment records from a directory in Apache Parquet format matching a filter, i.e.
3. Count the number of alignment records from a directory in Apache Parquet matching a range filter, i.e.
3. Count the number of alignment records from a directory in Apache Parquet partitioned by genomic start position matching a range filter, i.e.
4. Read all the alignment records and metadata from a directory in Apache Parquet format and write them out to a new directory in Apache Parquet format.
5. Convert the alignment records and metadata from a directory in Apache Parquet format and write to a single file in BAM format.

#### Block-gzipped (BGZF) VCF file benchmarks

Test files:

```
s3://giab/technical/platinum_genomes/bams_vcfs_4individules_2013/NA12878_S1_200x.genome.vcf.gz
```

1. Count the number of records in a block-gzipped (BGZF) VCF file.
2. Count the number of records in a block-gzipped (BGZF) VCF file matching a filter, i.e.
3. Count the number of records in an indexed block-gzipped (BGZF) VCF file matching a range filter, i.e.
4. Read the header and all records from a block-gzipped (BGZF) VCF file and write them out to a new block-gzipped (BGZF) VCF file.
5. Convert the header and all records from a block-gzipped (BGZF) VCF file per [bdg-formats schema](https://github.com/bigdatagenomics/bdg-formats) and write to Apache Parquet format.

#### Genotypes in Apache Parquet format benchmarks

Test files:

```bash
$ adam-submit transformGenotypes sample.vcf.bgz sample.genotypes.adam
$ adam-submit transformGenotypes -partition_by_start_pos sample.vcf.bgz sample.genotypes.1m.adam
```

1. Count the number of genotype records from a directory in Apache Parquet format.
2. Count the number of genotype records from a directory in Apache Parquet format matching a filter, i.e.
3. Count the number of genotype records from a directory in Apache Parquet matching a range filter, i.e.
3. Count the number of genotype records from a directory in Apache Parquet partitioned by genomic start position matching a range filter, i.e.
4. Read all the genotype records and metadata from a directory in Apache Parquet format and write them out to a new directory in Apache Parquet format.
5. Convert the genotype records and metadata from a directory in Apache Parquet format and write to a single file in block-gzipped (BGZF) VCF format.
