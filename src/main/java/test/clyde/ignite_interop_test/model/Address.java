package test.clyde.ignite_interop_test.model;

import org.apache.ignite.binary.BinaryObjectException;
import org.apache.ignite.binary.BinaryReader;
import org.apache.ignite.binary.BinaryWriter;
import org.apache.ignite.binary.Binarylizable;

public class Address implements Binarylizable {

	private String street;

	private int zip;

	@Override
	public void writeBinary(BinaryWriter writer) throws BinaryObjectException {
		writer.writeString("street", street);
		writer.writeInt("zip", zip);
	}

	@Override
	public void readBinary(BinaryReader reader) throws BinaryObjectException {
		street = reader.readString("street");
		zip = reader.readInt("zip");
	}

	@Override
	public String toString() {
		return String.format("Address [street=%s, zip=%s]", street, zip);
	}

	public String getStreet() {
		return street;
	}

	public int getZip() {
		return zip;
	}
}
