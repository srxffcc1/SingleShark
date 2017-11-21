/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.businessframehelp.db.ay;

import org.json.JSONException;

class AyJSONUtil {
    /**
     * Returns the input if it is a AyJSONUtil-permissable value; throws otherwise.
     */
    static double checkDouble(double d) throws JSONException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new JSONException("Forbidden numeric value: " + d);
        }
        return d;
    }

    /**
     * 
     * @todo  
     * @param value
     * @return  
     * @author admin
     * @created 2013-7-15  下午3:27:26
     * @update by:
     */
    static Boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.valueOf(((String) value));
        } else {
            return null;
        }
    }

    /**
     * 
     * @todo  
     * @param value
     * @return  
     * @author admin
     * @created 2013-7-15  下午3:27:41
     * @update by:
     */
    static Double toDouble(Object value) {
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.valueOf((String) value);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }

    /**
     * 
     * @todo  
     * @param value
     * @return  
     * @author admin
     * @created 2013-7-15  下午3:27:53
     * @update by:
     */
    static Integer toInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return (int) Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }

    /**
     * 
     * @todo  
     * @param value
     * @return  
     * @author admin
     * @created 2013-7-15  下午3:28:13
     * @update by:
     */
    static Long toLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return (long) Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }

    /**
     * 
     * @todo  
     * @param value
     * @return  
     * @author admin
     * @created 2013-7-15  下午3:28:23
     * @update by:
     */
    static String toString(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else if (value != null) {
            return String.valueOf(value);
        }
        return null;
    }

    /**
     * 
     * @todo  
     * @param indexOrName actual requiredType
     * @return  
     * @return @throws JSONException
     * @author admin
     * @created 2013-7-15  下午3:28:36
     * @update by:
     */
    public static JSONException typeMismatch(Object indexOrName, Object actual,
            String requiredType) throws JSONException {
        if (actual == null) {
            throw new JSONException("Value at " + indexOrName + " is null.");
        } else {
            throw new JSONException("Value " + actual + " at " + indexOrName
                    + " of type " + actual.getClass().getName()
                    + " cannot be converted to " + requiredType);
        }
    }

    /**
     * 
     * @todo  
     * @param actual requiredType
     * @return  
     * @return @throws JSONException
     * @author admin
     * @created 2013-7-15  下午3:29:11
     * @update by:
     */
    public static JSONException typeMismatch(Object actual, String requiredType)
            throws JSONException {
        if (actual == null) {
            throw new JSONException("Value is null.");
        } else {
            throw new JSONException("Value " + actual
                    + " of type " + actual.getClass().getName()
                    + " cannot be converted to " + requiredType);
        }
    }
}
