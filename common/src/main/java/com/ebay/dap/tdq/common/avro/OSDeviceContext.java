/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.ebay.dap.tdq.common.model.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class OSDeviceContext extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 7792136583004274047L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OSDeviceContext\",\"namespace\":\"com.ebay.dap.tdq.common.model.avro\",\"fields\":[{\"name\":\"osName\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"osVersion\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"manufacturer\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"doc\":\"Android related fields\",\"default\":null},{\"name\":\"androidId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"gadid\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"screenDpi\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"idfa\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"doc\":\"IOS related fields\",\"default\":null},{\"name\":\"screenScale\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"browserName\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"Browser related fields\"},{\"name\":\"browserVersion\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"gpcEnabled\",\"type\":\"boolean\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<OSDeviceContext> ENCODER =
      new BinaryMessageEncoder<OSDeviceContext>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OSDeviceContext> DECODER =
      new BinaryMessageDecoder<OSDeviceContext>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<OSDeviceContext> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<OSDeviceContext> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<OSDeviceContext> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<OSDeviceContext>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this OSDeviceContext to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a OSDeviceContext from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a OSDeviceContext instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static OSDeviceContext fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String osName;
   private java.lang.String osVersion;
  /** Android related fields */
   private java.lang.String manufacturer;
   private java.lang.String androidId;
   private java.lang.String gadid;
   private java.lang.String screenDpi;
  /** IOS related fields */
   private java.lang.String idfa;
   private java.lang.String screenScale;
  /** Browser related fields */
   private java.lang.String browserName;
   private java.lang.String browserVersion;
   private boolean gpcEnabled;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OSDeviceContext() {}

  /**
   * All-args constructor.
   * @param osName The new value for osName
   * @param osVersion The new value for osVersion
   * @param manufacturer Android related fields
   * @param androidId The new value for androidId
   * @param gadid The new value for gadid
   * @param screenDpi The new value for screenDpi
   * @param idfa IOS related fields
   * @param screenScale The new value for screenScale
   * @param browserName Browser related fields
   * @param browserVersion The new value for browserVersion
   * @param gpcEnabled The new value for gpcEnabled
   */
  public OSDeviceContext(java.lang.String osName, java.lang.String osVersion, java.lang.String manufacturer, java.lang.String androidId, java.lang.String gadid, java.lang.String screenDpi, java.lang.String idfa, java.lang.String screenScale, java.lang.String browserName, java.lang.String browserVersion, java.lang.Boolean gpcEnabled) {
    this.osName = osName;
    this.osVersion = osVersion;
    this.manufacturer = manufacturer;
    this.androidId = androidId;
    this.gadid = gadid;
    this.screenDpi = screenDpi;
    this.idfa = idfa;
    this.screenScale = screenScale;
    this.browserName = browserName;
    this.browserVersion = browserVersion;
    this.gpcEnabled = gpcEnabled;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return osName;
    case 1: return osVersion;
    case 2: return manufacturer;
    case 3: return androidId;
    case 4: return gadid;
    case 5: return screenDpi;
    case 6: return idfa;
    case 7: return screenScale;
    case 8: return browserName;
    case 9: return browserVersion;
    case 10: return gpcEnabled;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: osName = value$ != null ? value$.toString() : null; break;
    case 1: osVersion = value$ != null ? value$.toString() : null; break;
    case 2: manufacturer = value$ != null ? value$.toString() : null; break;
    case 3: androidId = value$ != null ? value$.toString() : null; break;
    case 4: gadid = value$ != null ? value$.toString() : null; break;
    case 5: screenDpi = value$ != null ? value$.toString() : null; break;
    case 6: idfa = value$ != null ? value$.toString() : null; break;
    case 7: screenScale = value$ != null ? value$.toString() : null; break;
    case 8: browserName = value$ != null ? value$.toString() : null; break;
    case 9: browserVersion = value$ != null ? value$.toString() : null; break;
    case 10: gpcEnabled = (java.lang.Boolean)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'osName' field.
   * @return The value of the 'osName' field.
   */
  public java.lang.String getOsName() {
    return osName;
  }


  /**
   * Sets the value of the 'osName' field.
   * @param value the value to set.
   */
  public void setOsName(java.lang.String value) {
    this.osName = value;
  }

  /**
   * Gets the value of the 'osVersion' field.
   * @return The value of the 'osVersion' field.
   */
  public java.lang.String getOsVersion() {
    return osVersion;
  }


  /**
   * Sets the value of the 'osVersion' field.
   * @param value the value to set.
   */
  public void setOsVersion(java.lang.String value) {
    this.osVersion = value;
  }

  /**
   * Gets the value of the 'manufacturer' field.
   * @return Android related fields
   */
  public java.lang.String getManufacturer() {
    return manufacturer;
  }


  /**
   * Sets the value of the 'manufacturer' field.
   * Android related fields
   * @param value the value to set.
   */
  public void setManufacturer(java.lang.String value) {
    this.manufacturer = value;
  }

  /**
   * Gets the value of the 'androidId' field.
   * @return The value of the 'androidId' field.
   */
  public java.lang.String getAndroidId() {
    return androidId;
  }


  /**
   * Sets the value of the 'androidId' field.
   * @param value the value to set.
   */
  public void setAndroidId(java.lang.String value) {
    this.androidId = value;
  }

  /**
   * Gets the value of the 'gadid' field.
   * @return The value of the 'gadid' field.
   */
  public java.lang.String getGadid() {
    return gadid;
  }


  /**
   * Sets the value of the 'gadid' field.
   * @param value the value to set.
   */
  public void setGadid(java.lang.String value) {
    this.gadid = value;
  }

  /**
   * Gets the value of the 'screenDpi' field.
   * @return The value of the 'screenDpi' field.
   */
  public java.lang.String getScreenDpi() {
    return screenDpi;
  }


  /**
   * Sets the value of the 'screenDpi' field.
   * @param value the value to set.
   */
  public void setScreenDpi(java.lang.String value) {
    this.screenDpi = value;
  }

  /**
   * Gets the value of the 'idfa' field.
   * @return IOS related fields
   */
  public java.lang.String getIdfa() {
    return idfa;
  }


  /**
   * Sets the value of the 'idfa' field.
   * IOS related fields
   * @param value the value to set.
   */
  public void setIdfa(java.lang.String value) {
    this.idfa = value;
  }

  /**
   * Gets the value of the 'screenScale' field.
   * @return The value of the 'screenScale' field.
   */
  public java.lang.String getScreenScale() {
    return screenScale;
  }


  /**
   * Sets the value of the 'screenScale' field.
   * @param value the value to set.
   */
  public void setScreenScale(java.lang.String value) {
    this.screenScale = value;
  }

  /**
   * Gets the value of the 'browserName' field.
   * @return Browser related fields
   */
  public java.lang.String getBrowserName() {
    return browserName;
  }


  /**
   * Sets the value of the 'browserName' field.
   * Browser related fields
   * @param value the value to set.
   */
  public void setBrowserName(java.lang.String value) {
    this.browserName = value;
  }

  /**
   * Gets the value of the 'browserVersion' field.
   * @return The value of the 'browserVersion' field.
   */
  public java.lang.String getBrowserVersion() {
    return browserVersion;
  }


  /**
   * Sets the value of the 'browserVersion' field.
   * @param value the value to set.
   */
  public void setBrowserVersion(java.lang.String value) {
    this.browserVersion = value;
  }

  /**
   * Gets the value of the 'gpcEnabled' field.
   * @return The value of the 'gpcEnabled' field.
   */
  public boolean getGpcEnabled() {
    return gpcEnabled;
  }


  /**
   * Sets the value of the 'gpcEnabled' field.
   * @param value the value to set.
   */
  public void setGpcEnabled(boolean value) {
    this.gpcEnabled = value;
  }

  /**
   * Creates a new OSDeviceContext RecordBuilder.
   * @return A new OSDeviceContext RecordBuilder
   */
  public static com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder newBuilder() {
    return new com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder();
  }

  /**
   * Creates a new OSDeviceContext RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OSDeviceContext RecordBuilder
   */
  public static com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder newBuilder(com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder other) {
    if (other == null) {
      return new com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder();
    } else {
      return new com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder(other);
    }
  }

  /**
   * Creates a new OSDeviceContext RecordBuilder by copying an existing OSDeviceContext instance.
   * @param other The existing instance to copy.
   * @return A new OSDeviceContext RecordBuilder
   */
  public static com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder newBuilder(com.ebay.dap.tdq.common.model.avro.OSDeviceContext other) {
    if (other == null) {
      return new com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder();
    } else {
      return new com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder(other);
    }
  }

  /**
   * RecordBuilder for OSDeviceContext instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OSDeviceContext>
    implements org.apache.avro.data.RecordBuilder<OSDeviceContext> {

    private java.lang.String osName;
    private java.lang.String osVersion;
    /** Android related fields */
    private java.lang.String manufacturer;
    private java.lang.String androidId;
    private java.lang.String gadid;
    private java.lang.String screenDpi;
    /** IOS related fields */
    private java.lang.String idfa;
    private java.lang.String screenScale;
    /** Browser related fields */
    private java.lang.String browserName;
    private java.lang.String browserVersion;
    private boolean gpcEnabled;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.osName)) {
        this.osName = data().deepCopy(fields()[0].schema(), other.osName);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.osVersion)) {
        this.osVersion = data().deepCopy(fields()[1].schema(), other.osVersion);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.manufacturer)) {
        this.manufacturer = data().deepCopy(fields()[2].schema(), other.manufacturer);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.androidId)) {
        this.androidId = data().deepCopy(fields()[3].schema(), other.androidId);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.gadid)) {
        this.gadid = data().deepCopy(fields()[4].schema(), other.gadid);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.screenDpi)) {
        this.screenDpi = data().deepCopy(fields()[5].schema(), other.screenDpi);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.idfa)) {
        this.idfa = data().deepCopy(fields()[6].schema(), other.idfa);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.screenScale)) {
        this.screenScale = data().deepCopy(fields()[7].schema(), other.screenScale);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
      if (isValidValue(fields()[8], other.browserName)) {
        this.browserName = data().deepCopy(fields()[8].schema(), other.browserName);
        fieldSetFlags()[8] = other.fieldSetFlags()[8];
      }
      if (isValidValue(fields()[9], other.browserVersion)) {
        this.browserVersion = data().deepCopy(fields()[9].schema(), other.browserVersion);
        fieldSetFlags()[9] = other.fieldSetFlags()[9];
      }
      if (isValidValue(fields()[10], other.gpcEnabled)) {
        this.gpcEnabled = data().deepCopy(fields()[10].schema(), other.gpcEnabled);
        fieldSetFlags()[10] = other.fieldSetFlags()[10];
      }
    }

    /**
     * Creates a Builder by copying an existing OSDeviceContext instance
     * @param other The existing instance to copy.
     */
    private Builder(com.ebay.dap.tdq.common.model.avro.OSDeviceContext other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.osName)) {
        this.osName = data().deepCopy(fields()[0].schema(), other.osName);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.osVersion)) {
        this.osVersion = data().deepCopy(fields()[1].schema(), other.osVersion);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.manufacturer)) {
        this.manufacturer = data().deepCopy(fields()[2].schema(), other.manufacturer);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.androidId)) {
        this.androidId = data().deepCopy(fields()[3].schema(), other.androidId);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.gadid)) {
        this.gadid = data().deepCopy(fields()[4].schema(), other.gadid);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.screenDpi)) {
        this.screenDpi = data().deepCopy(fields()[5].schema(), other.screenDpi);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.idfa)) {
        this.idfa = data().deepCopy(fields()[6].schema(), other.idfa);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.screenScale)) {
        this.screenScale = data().deepCopy(fields()[7].schema(), other.screenScale);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.browserName)) {
        this.browserName = data().deepCopy(fields()[8].schema(), other.browserName);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.browserVersion)) {
        this.browserVersion = data().deepCopy(fields()[9].schema(), other.browserVersion);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.gpcEnabled)) {
        this.gpcEnabled = data().deepCopy(fields()[10].schema(), other.gpcEnabled);
        fieldSetFlags()[10] = true;
      }
    }

    /**
      * Gets the value of the 'osName' field.
      * @return The value.
      */
    public java.lang.String getOsName() {
      return osName;
    }


    /**
      * Sets the value of the 'osName' field.
      * @param value The value of 'osName'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setOsName(java.lang.String value) {
      validate(fields()[0], value);
      this.osName = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'osName' field has been set.
      * @return True if the 'osName' field has been set, false otherwise.
      */
    public boolean hasOsName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'osName' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearOsName() {
      osName = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'osVersion' field.
      * @return The value.
      */
    public java.lang.String getOsVersion() {
      return osVersion;
    }


    /**
      * Sets the value of the 'osVersion' field.
      * @param value The value of 'osVersion'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setOsVersion(java.lang.String value) {
      validate(fields()[1], value);
      this.osVersion = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'osVersion' field has been set.
      * @return True if the 'osVersion' field has been set, false otherwise.
      */
    public boolean hasOsVersion() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'osVersion' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearOsVersion() {
      osVersion = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'manufacturer' field.
      * Android related fields
      * @return The value.
      */
    public java.lang.String getManufacturer() {
      return manufacturer;
    }


    /**
      * Sets the value of the 'manufacturer' field.
      * Android related fields
      * @param value The value of 'manufacturer'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setManufacturer(java.lang.String value) {
      validate(fields()[2], value);
      this.manufacturer = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'manufacturer' field has been set.
      * Android related fields
      * @return True if the 'manufacturer' field has been set, false otherwise.
      */
    public boolean hasManufacturer() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'manufacturer' field.
      * Android related fields
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearManufacturer() {
      manufacturer = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'androidId' field.
      * @return The value.
      */
    public java.lang.String getAndroidId() {
      return androidId;
    }


    /**
      * Sets the value of the 'androidId' field.
      * @param value The value of 'androidId'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setAndroidId(java.lang.String value) {
      validate(fields()[3], value);
      this.androidId = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'androidId' field has been set.
      * @return True if the 'androidId' field has been set, false otherwise.
      */
    public boolean hasAndroidId() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'androidId' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearAndroidId() {
      androidId = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'gadid' field.
      * @return The value.
      */
    public java.lang.String getGadid() {
      return gadid;
    }


    /**
      * Sets the value of the 'gadid' field.
      * @param value The value of 'gadid'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setGadid(java.lang.String value) {
      validate(fields()[4], value);
      this.gadid = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'gadid' field has been set.
      * @return True if the 'gadid' field has been set, false otherwise.
      */
    public boolean hasGadid() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'gadid' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearGadid() {
      gadid = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'screenDpi' field.
      * @return The value.
      */
    public java.lang.String getScreenDpi() {
      return screenDpi;
    }


    /**
      * Sets the value of the 'screenDpi' field.
      * @param value The value of 'screenDpi'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setScreenDpi(java.lang.String value) {
      validate(fields()[5], value);
      this.screenDpi = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'screenDpi' field has been set.
      * @return True if the 'screenDpi' field has been set, false otherwise.
      */
    public boolean hasScreenDpi() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'screenDpi' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearScreenDpi() {
      screenDpi = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'idfa' field.
      * IOS related fields
      * @return The value.
      */
    public java.lang.String getIdfa() {
      return idfa;
    }


    /**
      * Sets the value of the 'idfa' field.
      * IOS related fields
      * @param value The value of 'idfa'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setIdfa(java.lang.String value) {
      validate(fields()[6], value);
      this.idfa = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'idfa' field has been set.
      * IOS related fields
      * @return True if the 'idfa' field has been set, false otherwise.
      */
    public boolean hasIdfa() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'idfa' field.
      * IOS related fields
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearIdfa() {
      idfa = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'screenScale' field.
      * @return The value.
      */
    public java.lang.String getScreenScale() {
      return screenScale;
    }


    /**
      * Sets the value of the 'screenScale' field.
      * @param value The value of 'screenScale'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setScreenScale(java.lang.String value) {
      validate(fields()[7], value);
      this.screenScale = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'screenScale' field has been set.
      * @return True if the 'screenScale' field has been set, false otherwise.
      */
    public boolean hasScreenScale() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'screenScale' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearScreenScale() {
      screenScale = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'browserName' field.
      * Browser related fields
      * @return The value.
      */
    public java.lang.String getBrowserName() {
      return browserName;
    }


    /**
      * Sets the value of the 'browserName' field.
      * Browser related fields
      * @param value The value of 'browserName'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setBrowserName(java.lang.String value) {
      validate(fields()[8], value);
      this.browserName = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'browserName' field has been set.
      * Browser related fields
      * @return True if the 'browserName' field has been set, false otherwise.
      */
    public boolean hasBrowserName() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'browserName' field.
      * Browser related fields
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearBrowserName() {
      browserName = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'browserVersion' field.
      * @return The value.
      */
    public java.lang.String getBrowserVersion() {
      return browserVersion;
    }


    /**
      * Sets the value of the 'browserVersion' field.
      * @param value The value of 'browserVersion'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setBrowserVersion(java.lang.String value) {
      validate(fields()[9], value);
      this.browserVersion = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'browserVersion' field has been set.
      * @return True if the 'browserVersion' field has been set, false otherwise.
      */
    public boolean hasBrowserVersion() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'browserVersion' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearBrowserVersion() {
      browserVersion = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /**
      * Gets the value of the 'gpcEnabled' field.
      * @return The value.
      */
    public boolean getGpcEnabled() {
      return gpcEnabled;
    }


    /**
      * Sets the value of the 'gpcEnabled' field.
      * @param value The value of 'gpcEnabled'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder setGpcEnabled(boolean value) {
      validate(fields()[10], value);
      this.gpcEnabled = value;
      fieldSetFlags()[10] = true;
      return this;
    }

    /**
      * Checks whether the 'gpcEnabled' field has been set.
      * @return True if the 'gpcEnabled' field has been set, false otherwise.
      */
    public boolean hasGpcEnabled() {
      return fieldSetFlags()[10];
    }


    /**
      * Clears the value of the 'gpcEnabled' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.OSDeviceContext.Builder clearGpcEnabled() {
      fieldSetFlags()[10] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OSDeviceContext build() {
      try {
        OSDeviceContext record = new OSDeviceContext();
        record.osName = fieldSetFlags()[0] ? this.osName : (java.lang.String) defaultValue(fields()[0]);
        record.osVersion = fieldSetFlags()[1] ? this.osVersion : (java.lang.String) defaultValue(fields()[1]);
        record.manufacturer = fieldSetFlags()[2] ? this.manufacturer : (java.lang.String) defaultValue(fields()[2]);
        record.androidId = fieldSetFlags()[3] ? this.androidId : (java.lang.String) defaultValue(fields()[3]);
        record.gadid = fieldSetFlags()[4] ? this.gadid : (java.lang.String) defaultValue(fields()[4]);
        record.screenDpi = fieldSetFlags()[5] ? this.screenDpi : (java.lang.String) defaultValue(fields()[5]);
        record.idfa = fieldSetFlags()[6] ? this.idfa : (java.lang.String) defaultValue(fields()[6]);
        record.screenScale = fieldSetFlags()[7] ? this.screenScale : (java.lang.String) defaultValue(fields()[7]);
        record.browserName = fieldSetFlags()[8] ? this.browserName : (java.lang.String) defaultValue(fields()[8]);
        record.browserVersion = fieldSetFlags()[9] ? this.browserVersion : (java.lang.String) defaultValue(fields()[9]);
        record.gpcEnabled = fieldSetFlags()[10] ? this.gpcEnabled : (java.lang.Boolean) defaultValue(fields()[10]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OSDeviceContext>
    WRITER$ = (org.apache.avro.io.DatumWriter<OSDeviceContext>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OSDeviceContext>
    READER$ = (org.apache.avro.io.DatumReader<OSDeviceContext>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    if (this.osName == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.osName);
    }

    if (this.osVersion == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.osVersion);
    }

    if (this.manufacturer == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.manufacturer);
    }

    if (this.androidId == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.androidId);
    }

    if (this.gadid == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.gadid);
    }

    if (this.screenDpi == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.screenDpi);
    }

    if (this.idfa == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.idfa);
    }

    if (this.screenScale == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.screenScale);
    }

    out.writeString(this.browserName);

    out.writeString(this.browserVersion);

    out.writeBoolean(this.gpcEnabled);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      if (in.readIndex() != 1) {
        in.readNull();
        this.osName = null;
      } else {
        this.osName = in.readString();
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.osVersion = null;
      } else {
        this.osVersion = in.readString();
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.manufacturer = null;
      } else {
        this.manufacturer = in.readString();
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.androidId = null;
      } else {
        this.androidId = in.readString();
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.gadid = null;
      } else {
        this.gadid = in.readString();
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.screenDpi = null;
      } else {
        this.screenDpi = in.readString();
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.idfa = null;
      } else {
        this.idfa = in.readString();
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.screenScale = null;
      } else {
        this.screenScale = in.readString();
      }

      this.browserName = in.readString();

      this.browserVersion = in.readString();

      this.gpcEnabled = in.readBoolean();

    } else {
      for (int i = 0; i < 11; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          if (in.readIndex() != 1) {
            in.readNull();
            this.osName = null;
          } else {
            this.osName = in.readString();
          }
          break;

        case 1:
          if (in.readIndex() != 1) {
            in.readNull();
            this.osVersion = null;
          } else {
            this.osVersion = in.readString();
          }
          break;

        case 2:
          if (in.readIndex() != 1) {
            in.readNull();
            this.manufacturer = null;
          } else {
            this.manufacturer = in.readString();
          }
          break;

        case 3:
          if (in.readIndex() != 1) {
            in.readNull();
            this.androidId = null;
          } else {
            this.androidId = in.readString();
          }
          break;

        case 4:
          if (in.readIndex() != 1) {
            in.readNull();
            this.gadid = null;
          } else {
            this.gadid = in.readString();
          }
          break;

        case 5:
          if (in.readIndex() != 1) {
            in.readNull();
            this.screenDpi = null;
          } else {
            this.screenDpi = in.readString();
          }
          break;

        case 6:
          if (in.readIndex() != 1) {
            in.readNull();
            this.idfa = null;
          } else {
            this.idfa = in.readString();
          }
          break;

        case 7:
          if (in.readIndex() != 1) {
            in.readNull();
            this.screenScale = null;
          } else {
            this.screenScale = in.readString();
          }
          break;

        case 8:
          this.browserName = in.readString();
          break;

        case 9:
          this.browserVersion = in.readString();
          break;

        case 10:
          this.gpcEnabled = in.readBoolean();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










