/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.carbondata.core.util.comparator;

import java.math.BigDecimal;

import org.apache.carbondata.core.metadata.datatype.DataType;
import org.apache.carbondata.core.metadata.datatype.DataTypes;
import org.apache.carbondata.core.util.ByteUtil;

public final class Comparator {

  //Comparators are made static so that only one instance is generated
  private static final SerializableComparator BOOLEAN  = new BooleanSerializableComparator();
  private static final SerializableComparator INT = new IntSerializableComparator();
  private static final SerializableComparator SHORT = new ShortSerializableComparator();
  private static final SerializableComparator DOUBLE = new DoubleSerializableComparator();
  private static final SerializableComparator FLOAT = new FloatSerializableComparator();
  private static final SerializableComparator LONG = new LongSerializableComparator();
  private static final SerializableComparator DECIMAL  = new BigDecimalSerializableComparator();
  private static final SerializableComparator BYTE = new ByteArraySerializableComparator();

  public static SerializableComparator getComparator(DataType dataType) {
    if (dataType == DataTypes.DATE || dataType == DataTypes.TIMESTAMP) {
      return LONG;
    } else if (dataType == DataTypes.STRING) {
      return BYTE;
    } else {
      return getComparatorByDataTypeForMeasure(dataType);
    }
  }

  /**
   * create Comparator for Measure Datatype
   *
   * @param dataType
   * @return
   */
  public static SerializableComparator getComparatorByDataTypeForMeasure(DataType dataType) {
    if (dataType == DataTypes.BOOLEAN) {
      return BOOLEAN;
    } else if (dataType == DataTypes.INT) {
      return INT;
    } else if (dataType == DataTypes.SHORT) {
      return SHORT;
    } else if (dataType == DataTypes.LONG) {
      return LONG;
    } else if (dataType == DataTypes.DOUBLE) {
      return DOUBLE;
    } else if (dataType == DataTypes.FLOAT) {
      return FLOAT;
    } else if (DataTypes.isDecimal(dataType)) {
      return DECIMAL;
    } else if (dataType == DataTypes.BYTE) {
      return BYTE;
    } else {
      throw new IllegalArgumentException("Unsupported data type: " + dataType.getName());
    }
  }
}

class ByteArraySerializableComparator implements SerializableComparator {
  @Override public int compare(Object key1, Object key2) {
    if (key1 instanceof Byte) {
      return ((Byte) key1).compareTo((Byte) key2);
    }
    return ByteUtil.compare((byte[]) key1, (byte[]) key2);
  }
}

class BooleanSerializableComparator implements SerializableComparator {
  @Override
  public int compare(Object key1, Object key2) {
    if (key1 == null && key2 == null) {
      return 0;
    } else if (key1 == null) {
      return -1;
    } else if (key2 == null) {
      return 1;
    }
    if (Boolean.compare((boolean) key1, (boolean) key2) < 0) {
      return -1;
    } else if (Boolean.compare((boolean) key1, (boolean) key2) > 0) {
      return 1;
    } else {
      return 0;
    }
  }
}

class IntSerializableComparator implements SerializableComparator {
  @Override public int compare(Object key1, Object key2) {
    if (key1 == null && key2 == null) {
      return 0;
    } else if (key1 == null) {
      return -1;
    } else if (key2 == null) {
      return 1;
    }
    if ((int) key1 < (int) key2) {
      return -1;
    } else if ((int) key1 > (int) key2) {
      return 1;
    } else {
      return 0;
    }
  }
}

class ShortSerializableComparator implements SerializableComparator {
  @Override public int compare(Object key1, Object key2) {
    if (key1 == null && key2 == null) {
      return 0;
    } else if (key1 == null) {
      return -1;
    } else if (key2 == null) {
      return 1;
    }
    if ((short) key1 < (short) key2) {
      return -1;
    } else if ((short) key1 > (short) key2) {
      return 1;
    } else {
      return 0;
    }
  }
}

class DoubleSerializableComparator implements SerializableComparator {
  @Override public int compare(Object key1, Object key2) {
    if (key1 == null && key2 == null) {
      return 0;
    } else if (key1 == null) {
      return -1;
    } else if (key2 == null) {
      return 1;
    }
    return ((Double)key1).compareTo((Double)key2);
  }
}

class FloatSerializableComparator implements SerializableComparator {
  @Override public int compare(Object key1, Object key2) {
    if (key1 == null && key2 == null) {
      return 0;
    } else if (key1 == null) {
      return -1;
    } else if (key2 == null) {
      return 1;
    }
    return ((Float) key1).compareTo((Float) key2);
  }
}

class LongSerializableComparator implements SerializableComparator {
  @Override public int compare(Object key1, Object key2) {
    if (key1 == null && key2 == null) {
      return 0;
    } else if (key1 == null) {
      return -1;
    } else if (key2 == null) {
      return 1;
    }
    if ((long) key1 < (long) key2) {
      return -1;
    } else if ((long) key1 > (long) key2) {
      return 1;
    } else {
      return 0;
    }
  }
}

class BigDecimalSerializableComparator implements SerializableComparator {
  @Override public int compare(Object key1, Object key2) {
    if (key1 == null && key2 == null) {
      return 0;
    } else if (key1 == null) {
      return -1;
    } else if (key2 == null) {
      return 1;
    }
    return ((BigDecimal) key1).compareTo((BigDecimal) key2);
  }
}
