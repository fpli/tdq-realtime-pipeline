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
public class DataMetaContext extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -1051659920577398346L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"DataMetaContext\",\"namespace\":\"com.ebay.dap.tdq.common.model.avro\",\"fields\":[{\"name\":\"eventSchemaVersion\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<DataMetaContext> ENCODER =
      new BinaryMessageEncoder<DataMetaContext>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<DataMetaContext> DECODER =
      new BinaryMessageDecoder<DataMetaContext>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<DataMetaContext> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<DataMetaContext> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<DataMetaContext> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<DataMetaContext>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this DataMetaContext to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a DataMetaContext from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a DataMetaContext instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static DataMetaContext fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String eventSchemaVersion;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public DataMetaContext() {}

  /**
   * All-args constructor.
   * @param eventSchemaVersion The new value for eventSchemaVersion
   */
  public DataMetaContext(java.lang.String eventSchemaVersion) {
    this.eventSchemaVersion = eventSchemaVersion;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return eventSchemaVersion;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: eventSchemaVersion = value$ != null ? value$.toString() : null; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'eventSchemaVersion' field.
   * @return The value of the 'eventSchemaVersion' field.
   */
  public java.lang.String getEventSchemaVersion() {
    return eventSchemaVersion;
  }


  /**
   * Sets the value of the 'eventSchemaVersion' field.
   * @param value the value to set.
   */
  public void setEventSchemaVersion(java.lang.String value) {
    this.eventSchemaVersion = value;
  }

  /**
   * Creates a new DataMetaContext RecordBuilder.
   * @return A new DataMetaContext RecordBuilder
   */
  public static com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder newBuilder() {
    return new com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder();
  }

  /**
   * Creates a new DataMetaContext RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new DataMetaContext RecordBuilder
   */
  public static com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder newBuilder(com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder other) {
    if (other == null) {
      return new com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder();
    } else {
      return new com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder(other);
    }
  }

  /**
   * Creates a new DataMetaContext RecordBuilder by copying an existing DataMetaContext instance.
   * @param other The existing instance to copy.
   * @return A new DataMetaContext RecordBuilder
   */
  public static com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder newBuilder(com.ebay.dap.tdq.common.model.avro.DataMetaContext other) {
    if (other == null) {
      return new com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder();
    } else {
      return new com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder(other);
    }
  }

  /**
   * RecordBuilder for DataMetaContext instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<DataMetaContext>
    implements org.apache.avro.data.RecordBuilder<DataMetaContext> {

    private java.lang.String eventSchemaVersion;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.eventSchemaVersion)) {
        this.eventSchemaVersion = data().deepCopy(fields()[0].schema(), other.eventSchemaVersion);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing DataMetaContext instance
     * @param other The existing instance to copy.
     */
    private Builder(com.ebay.dap.tdq.common.model.avro.DataMetaContext other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.eventSchemaVersion)) {
        this.eventSchemaVersion = data().deepCopy(fields()[0].schema(), other.eventSchemaVersion);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'eventSchemaVersion' field.
      * @return The value.
      */
    public java.lang.String getEventSchemaVersion() {
      return eventSchemaVersion;
    }


    /**
      * Sets the value of the 'eventSchemaVersion' field.
      * @param value The value of 'eventSchemaVersion'.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder setEventSchemaVersion(java.lang.String value) {
      validate(fields()[0], value);
      this.eventSchemaVersion = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'eventSchemaVersion' field has been set.
      * @return True if the 'eventSchemaVersion' field has been set, false otherwise.
      */
    public boolean hasEventSchemaVersion() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'eventSchemaVersion' field.
      * @return This builder.
      */
    public com.ebay.dap.tdq.common.model.avro.DataMetaContext.Builder clearEventSchemaVersion() {
      eventSchemaVersion = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DataMetaContext build() {
      try {
        DataMetaContext record = new DataMetaContext();
        record.eventSchemaVersion = fieldSetFlags()[0] ? this.eventSchemaVersion : (java.lang.String) defaultValue(fields()[0]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<DataMetaContext>
    WRITER$ = (org.apache.avro.io.DatumWriter<DataMetaContext>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<DataMetaContext>
    READER$ = (org.apache.avro.io.DatumReader<DataMetaContext>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.eventSchemaVersion);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.eventSchemaVersion = in.readString();

    } else {
      for (int i = 0; i < 1; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.eventSchemaVersion = in.readString();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}









