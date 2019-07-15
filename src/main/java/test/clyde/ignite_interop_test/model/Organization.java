package test.clyde.ignite_interop_test.model;

import org.apache.ignite.binary.BinaryObjectException;
import org.apache.ignite.binary.BinaryReader;
import org.apache.ignite.binary.BinaryWriter;
import org.apache.ignite.binary.Binarylizable;

public class Organization implements Binarylizable {

	private String name;

	private Address addr;

	@Override
	public void writeBinary(BinaryWriter writer) throws BinaryObjectException {
		writer.writeString("name", name);
		writer.writeObject("addr", addr);
	}

	@Override
	public void readBinary(BinaryReader reader) throws BinaryObjectException {
		name = reader.readString("name");
		addr = reader.readObject("addr");
	}

	@Override
	public String toString() {
		return String.format("Organization [name=%s, addr=%s]", name, addr);
	}

	public String getName() {
		return name;
	}

	public Address getAddr() {
		return addr;
	}
}
