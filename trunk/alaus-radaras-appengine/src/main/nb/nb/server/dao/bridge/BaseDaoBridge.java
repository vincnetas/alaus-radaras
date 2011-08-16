/**
 * 
 */
package nb.server.dao.bridge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nb.server.dao.BaseDao;
import nb.shared.model.BaseObject;
import nb.shared.model.BaseObject.State;
import alaus.radaras.shared.model.Updatable;

/**
 * @author Vincentas Vienozinskis
 *
 */
public abstract class BaseDaoBridge<T extends BaseObject, V extends Updatable> implements BaseDao<T> {

    protected abstract alaus.radaras.server.dao.BaseDao<V> getBaseDao();
    
    protected abstract T convert(V value);
    
    protected List<T> convert(List<V> list) {
        List<T> result = new ArrayList<T>(list.size());
        
        for (V pub : list) {
            result.add(convert(pub));
        }
        
        return result;
    }
    
	private long lastTime = 0;
	
	private List<T> storedList;
	
	public List<T> getAll() {
		if (System.currentTimeMillis() - lastTime > 1000 * 60) {
			storedList = convert(getBaseDao().getApproved());
			lastTime = System.currentTimeMillis();
		}
		
		return storedList;
	}

    
    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#readCurrent(java.lang.String)
     */
    @Override
    public T readCurrent(String objectId) {
        return convert(getBaseDao().get(objectId));
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#create(nb.shared.model.BaseObject)
     */
    @Override
    public T create(T object) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#read(java.lang.String)
     */
    @Override
    public T read(String id) {
        return convert(getBaseDao().get(id));
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#read(java.util.List)
     */
    @Override
    public List<T> read(List<String> ids) {
        List<T> result = new ArrayList<T>();
        
        for (String id : ids) {
            result.add(read(id));
        }
        
        return result;
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#update(nb.shared.model.BaseObject)
     */
    @Override
    public T update(T object) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#delete(java.lang.String)
     */
    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#readDeleted(java.lang.String)
     */
    @Override
    public T readDeleted(String objectId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#readDeleted(java.util.Date)
     */
    @Override
    public List<T> readDeleted(Date since) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#readUpdated(java.util.Date)
     */
    @Override
    public List<T> readUpdated(Date since) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#read(java.lang.String, nb.shared.model.BaseObject.State)
     */
    @Override
    public List<T> read(String objectId, State state) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#readAll(java.lang.String)
     */
    @Override
    public List<T> readAll(String objectId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#findBy(nb.shared.model.BaseObject)
     */
    @Override
    public List<T> findBy(T filter) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see nb.server.dao.BaseDao#getFirstInstance(java.lang.String)
     */
    @Override
    public T getFirstInstance(String objectId) {
        // TODO Auto-generated method stub
        return null;
    }

	/* (non-Javadoc)
	 * @see nb.server.dao.BaseDao#readAll()
	 */
	@Override
	public List<T> readAll() {
		return convert(getBaseDao().getApproved());
	}
    
    
}
