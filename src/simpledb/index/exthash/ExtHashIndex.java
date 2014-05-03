package simpledb.index.exthash;

import simpledb.index.Index;
import simpledb.index.hash.HashIndex;
import simpledb.query.Constant;
import simpledb.query.TableScan;
import simpledb.record.RID;
import simpledb.record.Schema;
import simpledb.record.TableInfo;
import simpledb.tx.Transaction;

/**
 * CS 4432 Creation
 * 
 * A extensible hash implementation of the Index interface.
 *
 */
public class ExtHashIndex implements Index {
	private SecondHashIndex index;
	
	/**
	 * Opens a extensible hash index for the specified index.
	 * @param idxname the name of the index
	 * @param sch the schema of the index records
	 * @param tx the calling transaction
	 */
	public ExtHashIndex(String idxname, Schema sch, Transaction tx) {
		String name = "internal" + idxname;
//		index = new SecondHashIndex(name, sch, tx);
		index = new SecondHashIndex(idxname, sch, tx);
	}

	/**
	 * Positions the index before the first index record
	 * having the specified search key.
	 * The method hashes the search key to determine the bucket,
	 * and then opens a table scan on the file
	 * corresponding to the bucket.
	 * The table scan for the previous bucket (if any) is closed.
	 * @see simpledb.index.Index#beforeFirst(simpledb.query.Constant)
	 */
	public void beforeFirst(Constant searchkey) {
	/*	close();
		this.searchkey = searchkey;
		int bucket = 0;
		String tblname = idxname + bucket;
		TableInfo ti = new TableInfo(tblname, sch);
		
		ts = new TableScan(ti, tx);*/
		index.beforeFirst(searchkey);
	}

	/**
	 * Moves to the next record having the search key.
	 * The method loops through the table scan for the bucket,
	 * looking for a matching record, and returning false
	 * if there are no more such records.
	 * @see simpledb.index.Index#next()
	 */
	public boolean next() {
		/*while (ts.next())
			if (ts.getVal("dataval").equals(searchkey))
				return true;
		return false;*/
		return index.next();
	}

	/**
	 * Retrieves the dataRID from the current record
	 * in the table scan for the bucket.
	 * @see simpledb.index.Index#getDataRid()
	 */
	public RID getDataRid() {
		/*int blknum = ts.getInt("block");
		int id = ts.getInt("id");
		return new RID(blknum, id);*/
		return index.getDataRid();
	}

	/**
	 * Inserts a new record into the table scan for the bucket.
	 * @see simpledb.index.Index#insert(simpledb.query.Constant, simpledb.record.RID)
	 */
	public void insert(Constant val, RID rid) {
		/*beforeFirst(val);
		ts.insert();
		ts.setInt("block", rid.blockNumber());
		ts.setInt("id", rid.id());
		ts.setVal("dataval", val);*/
		index.insert(val, rid);
	}

	/**
	 * Deletes the specified record from the table scan for
	 * the bucket.  The method starts at the beginning of the
	 * scan, and loops through the records until the
	 * specified record is found.
	 * @see simpledb.index.Index#delete(simpledb.query.Constant, simpledb.record.RID)
	 */
	public void delete(Constant val, RID rid) {
		
		System.out.println("delete() is called");	//TODO delete
		
		/*beforeFirst(val);
		while(next())
			if (getDataRid().equals(rid)) {
				ts.delete();
				return;
			}*/
		index.delete(val, rid);	
	}

	/**
	 * Closes the index by closing the current table scan.
	 * @see simpledb.index.Index#close()
	 */
	public void close() {
		/*if (ts != null)
			ts.close();*/
		index.close();
	}

	/**
	 * Returns the cost of searching an index file having the
	 * specified number of blocks.
	 * The method assumes that all buckets are about the
	 * same size, and so the cost is simply the size of
	 * the bucket.
	 * @param numblocks the number of blocks of index records
	 * @param rpb the number of records per block (not used here)
	 * @return the cost of traversing the index
	 */
	public static int searchCost(int numblocks, int rpb){
		return numblocks / HashIndex.NUM_BUCKETS;
	}
	
	
}
