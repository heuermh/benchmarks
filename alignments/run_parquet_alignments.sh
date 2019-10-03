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
#ADAM="adam-shell --conf spark.kryo.registrationRequired=true"

SAMPLE="NA12878.alignedHg38.duplicateMarked.baseRealigned"
export INPUT="$SAMPLE.alignments.adam"
export OUTPUT="$SAMPLE.out.bam"

time ($ADAM -i convert_parquet_alignments_adam_dataset.scala &> /dev/null)
rm -Rf $OUTPUT

time ($ADAM -i convert_parquet_alignments_adam_rdd.scala &> /dev/null)
rm -Rf $OUTPUT

time ($ADAM -i convert_parquet_alignments_disq_adam.scala &> /dev/null)
rm -Rf $OUTPUT

#time ($ADAM -i convert_parquet_alignments_disq_convert.scala &> /dev/null)
#rm -Rf $SAMPLE.alignments.adam

export OUTPUT="$SAMPLE.out.cram"

# https://github.com/bigdatagenomics/adam/issues/2214

#time ($ADAM -i convert_parquet_alignments_adam_dataset.scala &> /dev/null)
#rm -Rf $OUTPUT

#time ($ADAM -i convert_parquet_alignments_adam_rdd.scala &> /dev/null)
#rm -Rf $OUTPUT

#time ($ADAM -i convert_parquet_alignments_disq_adam.scala &> /dev/null)
#rm -Rf $OUTPUT

#time ($ADAM -i convert_parquet_alignments_disq_convert.scala &> /dev/null)
#rm -Rf $SAMPLE.alignments.adam

time ($ADAM -i count_parquet_alignments_adam_dataframe.scala &> /dev/null)
time ($ADAM -i count_parquet_alignments_adam_dataset.scala &> /dev/null)
time ($ADAM -i count_parquet_alignments_adam_rdd.scala &> /dev/null)
time ($ADAM -i count_parquet_alignments_sparksql.scala &> /dev/null)

time ($ADAM -i filter_parquet_alignments_adam_dataset.scala &> /dev/null)
time ($ADAM -i filter_parquet_alignments_adam_rdd.scala &> /dev/null)
time ($ADAM -i filter_parquet_alignments_sparksql.scala &> /dev/null)

time ($ADAM -i range_filter_parquet_alignments_adam_dataset.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_alignments_adam_rdd.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_alignments_sparksql.scala &> /dev/null)

export INPUT="$SAMPLE.alignments.1m.adam"

time ($ADAM -i range_filter_parquet_alignments_adam_partitioned_dataset.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_alignments_adam_partitioned_rdd.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_alignments_adam_partitioned_dataset.scala &> /dev/null)
time ($ADAM -i range_filter_parquet_alignments_adam_partitioned_rdd.scala &> /dev/null)

export INPUT="$SAMPLE.alignments.adam"
export OUTPUT="$SAMPLE.out.alignments.adam"

time ($ADAM -i rw_parquet_alignments_adam_dataframe.scala &> /dev/null)
rm -Rf $OUTPUT

time ($ADAM -i rw_parquet_alignments_adam_dataset.scala &> /dev/null)
rm -Rf $OUTPUT

time ($ADAM -i rw_parquet_alignments_adam_rdd.scala &> /dev/null)
rm -Rf $OUTPUT
