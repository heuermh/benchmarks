#!/bin/bash

#
# Copyright 2019 held jointly by the individual authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

set -x

#ADAM="adam-shell"
ADAM="../../adam/bin/adam-shell"

SAMPLE="HiSeq.10000"
export INPUT="$SAMPLE.genotypes.adam"
export OUTPUT="$SAMPLE.out.vcf.gz"

time ($ADAM -i convert_parquet_genotypes_adam_dataset.scala &> /dev/null)
rm -Rf $OUTPUT

time ($ADAM -i convert_parquet_genotypes_adam_rdd.scala &> /dev/null)
rm -Rf $OUTPUT

time ($ADAM -i convert_parquet_genotypes_disq_adam.scala &> /dev/null)
rm -Rf $OUTPUT

#time ($ADAM -i convert_parquet_genotypes_disq_convert.scala &> /dev/null)
#rm -Rf $SAMPLE.genotypes.adam

time ($ADAM -i count_parquet_genotypes_adam_dataframe.scala &> /dev/null)
time ($ADAM -i count_parquet_genotypes_adam_dataset.scala &> /dev/null)
time ($ADAM -i count_parquet_genotypes_adam_rdd.scala &> /dev/null)
time ($ADAM -i count_parquet_genotypes_sparksql.scala &> /dev/null)

time ($ADAM -i filter_parquet_genotypes_adam_dataset.scala &> /dev/null)
time ($ADAM -i filter_parquet_genotypes_adam_rdd.scala &> /dev/null)
time ($ADAM -i filter_parquet_genotypes_sparksql.scala &> /dev/null)

time ($ADAM -i range_filter_parquet_genotypes_adam_dataset.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_genotypes_adam_rdd.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_genotypes_sparksql.scala &> /dev/null)

export INPUT="$SAMPLE.genotypes.1m.adam"

time ($ADAM -i range_filter_parquet_genotypes_adam_partitioned_dataset.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_genotypes_adam_partitioned_rdd.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_genotypes_adam_partitioned_dataset.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_genotypes_adam_partitioned_rdd.scala &> /dev/null)

export INPUT="$SAMPLE.genotypes.adam"
export OUTPUT="$SAMPLE.out.genotypes.adam"

time ($ADAM -i rw_parquet_genotypes_adam_dataframe.scala &> /dev/null)
rm -Rf $OUTPUT

time ($ADAM -i rw_parquet_genotypes_adam_dataset.scala &> /dev/null)
rm -Rf $OUTPUT

time ($ADAM -i rw_parquet_genotypes_adam_rdd.scala &> /dev/null)
rm -Rf $OUTPUT
