package org.apache.lucene.search.similarities;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.lucene.search.Explanation;
import static org.apache.lucene.search.similarities.EasySimilarity.log2;

/**
 * The basic tf-idf model of randomness.
 * @lucene.experimental
 */ 
public class BasicModelIn extends BasicModel {
  @Override
  public final float score(EasyStats stats, float tfn) {
    int N = stats.getNumberOfDocuments();
    int n = stats.getDocFreq();
    return tfn * (float)(log2((N + 1) / (n + 0.5)));
  }
  
  @Override
  public final Explanation explain(EasyStats stats, float tfn) {
    Explanation result = new Explanation();
    result.setDescription(getClass().getSimpleName() + ", computed from: ");
    result.setValue(score(stats, tfn));
    result.addDetail(new Explanation(tfn, "tfn"));
    result.addDetail(
        new Explanation(stats.getNumberOfDocuments(), "numberOfDocuments"));
    result.addDetail(
        new Explanation(stats.getDocFreq(), "docFreq"));
    return result;
  }
}
