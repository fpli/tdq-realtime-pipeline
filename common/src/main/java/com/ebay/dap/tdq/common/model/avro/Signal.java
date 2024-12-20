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
public class Signal extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 4248281013124639765L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Signal\",\"namespace\":\"com.ebay.dap.tdq.common.model.avro\",\"fields\":[{\"name\":\"rheosHeader\",\"type\":{\"type\":\"record\",\"name\":\"RheosHeader\",\"doc\":\"This category of cols are generated in Rheos built-in process\",\"fields\":[{\"name\":\"eventCreateTimestamp\",\"type\":\"long\"},{\"name\":\"eventSentTimestamp\",\"type\":\"long\"},{\"name\":\"schemaId\",\"type\":\"int\"},{\"name\":\"eventId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}]},{\"name\":\"producerId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"signalInfo\",\"type\":{\"type\":\"record\",\"name\":\"SignalInfo\",\"fields\":[{\"name\":\"context\",\"type\":{\"type\":\"record\",\"name\":\"Context\",\"fields\":[{\"name\":\"auditContext\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"AuditContext\",\"fields\":[{\"name\":\"ip\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"stepLabel\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"received\",\"type\":\"long\"},{\"name\":\"emitted\",\"type\":\"long\"},{\"name\":\"rlogId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}},\"default\":[]},{\"name\":\"identityContext\",\"type\":{\"type\":\"record\",\"name\":\"UserIdentityContext\",\"doc\":\"This category of cols describe the customer identity info in ebay\",\"fields\":[{\"name\":\"identityId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"userId\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"userName\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"guid\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}},{\"name\":\"deviceContext\",\"type\":{\"type\":\"record\",\"name\":\"DeviceContext\",\"doc\":\"Device collection information, describe the infomation of customer's terminal\",\"fields\":[{\"name\":\"appId\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"appVersion\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"viewportWidth\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"viewportHeight\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"windowWidth\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"windowHeight\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"networkCarrier\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"networkConnectionType\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"theme\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"remoteIp\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"forwardFor\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"clientHints\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"avro.java.string\":\"String\"},\"default\":{}},{\"name\":\"deviceAdvertisingOptOut\",\"type\":[\"null\",\"boolean\"],\"default\":null},{\"name\":\"userAgent\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"deviceDetectionProperties\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"DeviceDetection\",\"fields\":[{\"name\":\"formFactor\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"experience\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"model\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}],\"default\":null},{\"name\":\"osDeviceContext\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"OSDeviceContext\",\"fields\":[{\"name\":\"osName\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"osVersion\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"manufacturer\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"doc\":\"Android related fields\",\"default\":null},{\"name\":\"androidId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"gadid\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"screenDpi\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"idfa\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"doc\":\"IOS related fields\",\"default\":null},{\"name\":\"screenScale\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"browserName\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"Browser related fields\"},{\"name\":\"browserVersion\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"gpcEnabled\",\"type\":\"boolean\"}]}],\"default\":null}]}},{\"name\":\"experimentationContext\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"ExperimentationContext\",\"fields\":[{\"name\":\"es\",\"type\":\"int\",\"doc\":\"ep site id - using com.ebay.globalenv.SiteEnum\",\"default\":901},{\"name\":\"ec\",\"type\":\"int\",\"doc\":\"ep channel id - using com.ebay.ep.core.cos.COSUtil\",\"default\":99},{\"name\":\"xt\",\"type\":{\"type\":\"array\",\"items\":\"long\"},\"doc\":\"set of experienced treatments\",\"default\":[]},{\"name\":\"ot\",\"type\":{\"type\":\"array\",\"items\":\"long\"},\"doc\":\"set of optin treatments\",\"default\":[]},{\"name\":\"os\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},\"doc\":\"qualification info for optin treatments\",\"default\":[]},{\"name\":\"eprlogid\",\"type\":[{\"type\":\"string\",\"avro.java.string\":\"String\"},\"null\"],\"doc\":\"rlogid of the host that perform qualification\",\"default\":\"\"},{\"name\":\"epcalenv\",\"type\":[{\"type\":\"string\",\"avro.java.string\":\"String\"},\"null\"],\"doc\":\"the CAL environmant of eprlogid\",\"default\":\"\"},{\"name\":\"qt\",\"type\":{\"type\":\"array\",\"items\":\"long\"},\"doc\":\"set of qualified treatments\",\"default\":[]},{\"name\":\"qc\",\"type\":{\"type\":\"array\",\"items\":\"long\"},\"doc\":\"set of contextual qualified treatments\",\"default\":[]},{\"name\":\"uit\",\"type\":[\"long\",\"null\"],\"doc\":\"User identification timestamp\",\"default\":0},{\"name\":\"mdbreftime\",\"type\":[\"long\",\"null\"],\"doc\":\"timestamp of last experiment metadata refresh from database\",\"default\":0},{\"name\":\"anyId\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"AnyId\",\"fields\":[{\"name\":\"val\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"AnyId value\",\"default\":\"\"},{\"name\":\"xt\",\"type\":{\"type\":\"array\",\"items\":\"long\"},\"doc\":\"set of experienced treatmentids for this AnyId value\",\"default\":[]}]}},\"doc\":\"Information for AnyId type EP\",\"default\":[]}]}],\"default\":null},{\"name\":\"pageInteractionContext\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"PageInteractionContext\",\"doc\":\"This category of cols include the fields which tracking system definition\\n* and describe a ebay page and its elements\",\"fields\":[{\"name\":\"pageId\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"siteId\",\"type\":\"int\"},{\"name\":\"countryId\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"userLang\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"url\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"referrer\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"siteSpeed\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"avro.java.string\":\"String\"},\"default\":{}},{\"name\":\"moduleId\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"linkId\",\"type\":[\"null\",\"int\"],\"default\":null}]}],\"default\":null},{\"name\":\"dataMetaContext\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"DataMetaContext\",\"fields\":[{\"name\":\"eventSchemaVersion\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}],\"default\":null},{\"name\":\"debuggingContext\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"DebuggingContext\",\"doc\":\"This category of cols may be used internally. It won't output to downstream in stateful processor.\\n* If some events throw a failure. Log some info to help debugging.\\n* And send the failed event to monitor topic for quality control and alert\",\"fields\":[{\"name\":\"schemaTransFailures\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"avro.java.string\":\"String\"}},{\"name\":\"ingestionFailures\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"avro.java.string\":\"String\"}},{\"name\":\"errorCode\",\"type\":[\"null\",\"long\"]},{\"name\":\"poolName\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}]},{\"name\":\"collectionServer\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"Stri","ng\"}]},{\"name\":\"trackingAgentVersion\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}]},{\"name\":\"others\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"avro.java.string\":\"String\"}}]}],\"default\":null},{\"name\":\"correlationContext\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"CorrelationContext\",\"doc\":\"This category of cols are used for event correlation in stateful processor\\n*  It won't output in singal delta but for stateful processor internal usage\",\"fields\":[{\"name\":\"ciid\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"trackableId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"pageViewId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"siid\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}],\"default\":null}]}},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"metadataId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"deltaTimestamp\",\"type\":\"long\"},{\"name\":\"sequenceId\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"fields\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"record\",\"name\":\"Field\",\"fields\":[{\"name\":\"type\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]},\"avro.java.string\":\"String\"},\"default\":{}}]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Signal> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Signal> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Signal> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Signal> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Signal> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Signal to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Signal from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Signal instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Signal fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private com.ebay.dap.tdq.common.model.avro.RheosHeader rheosHeader;
  private com.ebay.dap.tdq.common.model.avro.SignalInfo signalInfo;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Signal() {}

  /**
   * All-args constructor.
   * @param rheosHeader The new value for rheosHeader
   * @param signalInfo The new value for signalInfo
   */
  public Signal(com.ebay.dap.tdq.common.model.avro.RheosHeader rheosHeader, com.ebay.dap.tdq.common.model.avro.SignalInfo signalInfo) {
    this.rheosHeader = rheosHeader;
    this.signalInfo = signalInfo;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return rheosHeader;
    case 1: return signalInfo;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: rheosHeader = (com.ebay.dap.tdq.common.model.avro.RheosHeader)value$; break;
    case 1: signalInfo = (com.ebay.dap.tdq.common.model.avro.SignalInfo)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'rheosHeader' field.
   * @return The value of the 'rheosHeader' field.
   */
  public com.ebay.dap.tdq.common.model.avro.RheosHeader getRheosHeader() {
    return rheosHeader;
  }


  /**
   * Sets the value of the 'rheosHeader' field.
   * @param value the value to set.
   */
  public void setRheosHeader(com.ebay.dap.tdq.common.model.avro.RheosHeader value) {
    this.rheosHeader = value;
  }

  /**
   * Gets the value of the 'signalInfo' field.
   * @return The value of the 'signalInfo' field.
   */
  public com.ebay.dap.tdq.common.model.avro.SignalInfo getSignalInfo() {
    return signalInfo;
  }


  /**
   * Sets the value of the 'signalInfo' field.
   * @param value the value to set.
   */
  public void setSignalInfo(com.ebay.dap.tdq.common.model.avro.SignalInfo value) {
    this.signalInfo = value;
  }

  /**
   * Creates a new Signal RecordBuilder.
   * @return A new Signal RecordBuilder
   */
  public static com.ebay.dap.tdq.common.model.avro.Signal.Builder newBuilder() {
    return new com.ebay.dap.tdq.common.model.avro.Signal.Builder();
  }

  /**
   * Creates a new Signal RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Signal RecordBuilder
   */
  public static com.ebay.dap.tdq.common.model.avro.Signal.Builder newBuilder(com.ebay.dap.tdq.common.model.avro.Signal.Builder other) {
    if (other == null) {
      return new com.ebay.dap.tdq.common.model.avro.Signal.Builder();
    } else {
      return new com.ebay.dap.tdq.common.model.avro.Signal.Builder(other);
    }
  }

  /**
   * Creates a new Signal RecordBuilder by copying an existing Signal instance.
   * @param other The existing instance to copy.
   * @return A new Signal RecordBuilder
   */
  public static com.ebay.dap.tdq.common.model.avro.Signal.Builder newBuilder(com.ebay.dap.tdq.common.model.avro.Signal other) {
    if (other == null) {
      return new com.ebay.dap.tdq.common.model.avro.Signal.Builder();
    } else {
      return new com.ebay.dap.tdq.common.model.avro.Signal.Builder(other);
    }
  }

  /**
   * RecordBuilder for Signal instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Signal>
    implements org.apache.avro.data.RecordBuilder<Signal> {

    private com.ebay.dap.tdq.common.model.avro.RheosHeader rheosHeader;
    private com.ebay.dap.tdq.common.model.avro.RheosHeader.Builder rheosHeaderBuilder;
    private com.ebay.dap.tdq.common.model.avro.SignalInfo signalInfo;
    private com.ebay.dap.tdq.common.model.avro.SignalInfo.Builder signalInfoBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.ebay.dap.tdq.common.model.avro.Signal.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.rheosHeader)) {
        this.rheosHeader = data().deepCopy(fields()[0].schema(), other.rheosHeader);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (other.hasRheosHeaderBuilder()) {
        this.rheosHeaderBuilder = com.ebay.dap.tdq.common.model.avro.RheosHeader.newBuilder(other.getRheosHeaderBuilder());
      }
      if (isValidValue(fields()[1], other.signalInfo)) {
        this.signalInfo = data().deepCopy(fields()[1].schema(), other.signalInfo);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (other.hasSignalInfoBuilder()) {
        this.signalInfoBuilder = com.ebay.dap.tdq.common.model.avro.SignalInfo.newBuilder(other.getSignalInfoBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing Signal instance
     * @param other The existing instance to copy.
     */
    private Builder(com.ebay.dap.tdq.common.model.avro.Signal other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.rheosHeader)) {
        this.rheosHeader = data().deepCopy(fields()[0].schema(), other.rheosHeader);
        fieldSetFlags()[0] = true;
      }
      this.rheosHeaderBuilder = null;
      if (isValidValue(fields()[1], other.signalInfo)) {
        this.signalInfo = data().deepCopy(fields()[1].schema(), other.signalInfo);
        fieldSetFlags()[1] = true;
      }
      this.signalInfoBuilder = null;
    }

    /**
      * Gets the value of the 'rheosHeader' field.
      * @return The value.
      */
    public com.ebay.dap.tdq.common.model.avro.RheosHeader getRheosHeader() {
      return rheosHeader;
    }


    /**
      * Sets the value of the 'rheosHeader' field.
      * @param value The value of 'rheosHeader'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.Signal.Builder setRheosHeader(com.ebay.dap.tdq.common.model.avro.RheosHeader value) {
      validate(fields()[0], value);
      this.rheosHeaderBuilder = null;
      this.rheosHeader = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'rheosHeader' field has been set.
      * @return True if the 'rheosHeader' field has been set, false otherwise.
      */
    public boolean hasRheosHeader() {
      return fieldSetFlags()[0];
    }

    /**
     * Gets the Builder instance for the 'rheosHeader' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.ebay.dap.tdq.common.model.avro.RheosHeader.Builder getRheosHeaderBuilder() {
      if (rheosHeaderBuilder == null) {
        if (hasRheosHeader()) {
          setRheosHeaderBuilder(com.ebay.dap.tdq.common.model.avro.RheosHeader.newBuilder(rheosHeader));
        } else {
          setRheosHeaderBuilder(com.ebay.dap.tdq.common.model.avro.RheosHeader.newBuilder());
        }
      }
      return rheosHeaderBuilder;
    }

    /**
     * Sets the Builder instance for the 'rheosHeader' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */

    public com.ebay.dap.tdq.common.model.avro.Signal.Builder setRheosHeaderBuilder(com.ebay.dap.tdq.common.model.avro.RheosHeader.Builder value) {
      clearRheosHeader();
      rheosHeaderBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'rheosHeader' field has an active Builder instance
     * @return True if the 'rheosHeader' field has an active Builder instance
     */
    public boolean hasRheosHeaderBuilder() {
      return rheosHeaderBuilder != null;
    }

    /**
      * Clears the value of the 'rheosHeader' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.Signal.Builder clearRheosHeader() {
      rheosHeader = null;
      rheosHeaderBuilder = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'signalInfo' field.
      * @return The value.
      */
    public com.ebay.dap.tdq.common.model.avro.SignalInfo getSignalInfo() {
      return signalInfo;
    }


    /**
      * Sets the value of the 'signalInfo' field.
      * @param value The value of 'signalInfo'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.Signal.Builder setSignalInfo(com.ebay.dap.tdq.common.model.avro.SignalInfo value) {
      validate(fields()[1], value);
      this.signalInfoBuilder = null;
      this.signalInfo = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'signalInfo' field has been set.
      * @return True if the 'signalInfo' field has been set, false otherwise.
      */
    public boolean hasSignalInfo() {
      return fieldSetFlags()[1];
    }

    /**
     * Gets the Builder instance for the 'signalInfo' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.ebay.dap.tdq.common.model.avro.SignalInfo.Builder getSignalInfoBuilder() {
      if (signalInfoBuilder == null) {
        if (hasSignalInfo()) {
          setSignalInfoBuilder(com.ebay.dap.tdq.common.model.avro.SignalInfo.newBuilder(signalInfo));
        } else {
          setSignalInfoBuilder(com.ebay.dap.tdq.common.model.avro.SignalInfo.newBuilder());
        }
      }
      return signalInfoBuilder;
    }

    /**
     * Sets the Builder instance for the 'signalInfo' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */

    public com.ebay.dap.tdq.common.model.avro.Signal.Builder setSignalInfoBuilder(com.ebay.dap.tdq.common.model.avro.SignalInfo.Builder value) {
      clearSignalInfo();
      signalInfoBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'signalInfo' field has an active Builder instance
     * @return True if the 'signalInfo' field has an active Builder instance
     */
    public boolean hasSignalInfoBuilder() {
      return signalInfoBuilder != null;
    }

    /**
      * Clears the value of the 'signalInfo' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.Signal.Builder clearSignalInfo() {
      signalInfo = null;
      signalInfoBuilder = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Signal build() {
      try {
        Signal record = new Signal();
        if (rheosHeaderBuilder != null) {
          try {
            record.rheosHeader = this.rheosHeaderBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("rheosHeader"));
            throw e;
          }
        } else {
          record.rheosHeader = fieldSetFlags()[0] ? this.rheosHeader : (com.ebay.dap.tdq.common.model.avro.RheosHeader) defaultValue(fields()[0]);
        }
        if (signalInfoBuilder != null) {
          try {
            record.signalInfo = this.signalInfoBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("signalInfo"));
            throw e;
          }
        } else {
          record.signalInfo = fieldSetFlags()[1] ? this.signalInfo : (com.ebay.dap.tdq.common.model.avro.SignalInfo) defaultValue(fields()[1]);
        }
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Signal>
    WRITER$ = (org.apache.avro.io.DatumWriter<Signal>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Signal>
    READER$ = (org.apache.avro.io.DatumReader<Signal>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    this.rheosHeader.customEncode(out);

    this.signalInfo.customEncode(out);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      if (this.rheosHeader == null) {
        this.rheosHeader = new com.ebay.dap.tdq.common.model.avro.RheosHeader();
      }
      this.rheosHeader.customDecode(in);

      if (this.signalInfo == null) {
        this.signalInfo = new com.ebay.dap.tdq.common.model.avro.SignalInfo();
      }
      this.signalInfo.customDecode(in);

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          if (this.rheosHeader == null) {
            this.rheosHeader = new com.ebay.dap.tdq.common.model.avro.RheosHeader();
          }
          this.rheosHeader.customDecode(in);
          break;

        case 1:
          if (this.signalInfo == null) {
            this.signalInfo = new com.ebay.dap.tdq.common.model.avro.SignalInfo();
          }
          this.signalInfo.customDecode(in);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










