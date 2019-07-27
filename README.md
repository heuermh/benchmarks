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

ftp://ftp-trace.ncbi.nlm.nih.gov/giab/ftp/data/AshkenazimTrio/analysis/NIST_v4beta_SmallVariantDraftBenchmark_07192019/GIAB_SmallVariant_Benchmark_v4beta_GRCh37_HG002.vcf.gz
ftp://ftp-trace.ncbi.nlm.nih.gov/giab/ftp/data/AshkenazimTrio/analysis/NIST_v4beta_SmallVariantDraftBenchmark_07192019/GIAB_SmallVariant_Benchmark_v4beta_GRCh37_HG002.vcf.gz.tbi

http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/data_collections/1000G_2504_high_coverage/working/20190425_NYGC_GATK/

100G

http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/data_collections/1000G_2504_high_coverage/working/20190425_NYGC_GATK/CCDG_13607_B01_GRM_WGS_2019-02-19_chr2.recalibrated_variants.vcf.gz
http://ftp.1000genomes.ebi.ac.uk/vol1/ftp/data_collections/1000G_2504_high_coverage/working/20190425_NYGC_GATK/CCDG_13607_B01_GRM_WGS_2019-02-19_chr2.recalibrated_variants.vcf.gz.tbi

(aligned to ftp://ftp.1000genomes.ebi.ac.uk/vol1/ftp/technical/reference/GRCh38_reference_genome/GRCh38_full_analysis_set_plus_decoy_hla.fa)
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

#### Variants in Apache Parquet format benchmarks

Test files:

```bash
$ adam-submit transformVariants sample.vcf.bgz sample.variants.adam
$ adam-submit transformVariants -partition_by_start_pos sample.vcf.bgz sample.variants.1m.adam
```

1. Count the number of variant records from a directory in Apache Parquet format.
2. Count the number of variant records from a directory in Apache Parquet format matching a filter, i.e.
3. Count the number of variant records from a directory in Apache Parquet matching a range filter, i.e.
3. Count the number of variant records from a directory in Apache Parquet partitioned by genomic start position matching a range filter, i.e.
4. Read all the variant records and metadata from a directory in Apache Parquet format and write them out to a new directory in Apache Parquet format.
5. Convert the variant records and metadata from a directory in Apache Parquet format and write to a single file in block-gzipped (BGZF) VCF format.
