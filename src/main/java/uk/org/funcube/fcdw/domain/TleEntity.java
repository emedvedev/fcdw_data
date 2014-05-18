package uk.org.funcube.fcdw.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import uk.org.funcube.fcdw.satellite.TLE;

@Entity
@Table(name = "Tle")
public class TleEntity {
	
	@Id
	private long catnum;
	
	private double argper;
	private double bstar;
	private double drag;
	private double eccn;
	private double eo;
	private double epoch;
	private double incl;
	private double meanan;
	private double meanmo;
	private String name;
	private double nddot6;
	private double omega;
	private double raan;
	private double refepoch;
	private int setnum;
	private double xincl;
	private double mo;
	private double xndt2o;
	private double xno;
	private double xnodeo;
	private boolean deepspace;
	private int year;
	private Date createddate;
	private long orbitnum;
	
	public TleEntity() {
		
	}

	public TleEntity(final TLE tle) {
		argper = tle.getArgper();
		bstar = tle.getBstar();
		catnum = tle.getCatnum();
		drag = tle.getDrag();
		eccn = tle.getEccn();
		eo = tle.getEo();
		epoch = tle.getEpoch();
		incl = tle.getIncl();
		meanan =  tle.getMeanan();
		meanmo = tle.getMeanmo();
		name = tle.getName();
		nddot6 = tle.getNddot6();
		omega = tle.getOmegao();
		raan = tle.getRaan();
		refepoch = tle.getRefepoch();
		setnum = tle.getSetnum();
		xincl = tle.getXincl();
		mo = tle.getXmo();
		xndt2o = tle.getXndt2o();
		xno = tle.getXno();
		xnodeo = tle.getXnodeo();
		deepspace = tle.isDeepspace();
		year = tle.getYear();
		createddate = tle.getCreateddate();
		orbitnum = tle.getOrbitnum();
	}

	/**
	 * @param catnum
	 * @param argper
	 * @param bstar
	 * @param drag
	 * @param eccn
	 * @param eo
	 * @param epoch
	 * @param incl
	 * @param meanan
	 * @param meanmo
	 * @param name
	 * @param nddot6
	 * @param omega
	 * @param raan
	 * @param refepoch
	 * @param setnum
	 * @param xincl
	 * @param mo
	 * @param xndt2o
	 * @param xno
	 * @param xnodeo
	 * @param deepspace
	 */
	public TleEntity(int catnum, double argper, double bstar, double drag,
			double eccn, double eo, double epoch, double incl, double meanan,
			double meanmo, String name, double nddot6, double omega,
			double raan, double refepoch, int setnum, double xincl, double mo,
			double xndt2o, double xno, double xnodeo, boolean deepspace,
			int year, Date createddate, long orbitnum) {
		super();
		this.catnum = catnum;
		this.argper = argper;
		this.bstar = bstar;
		this.drag = drag;
		this.eccn = eccn;
		this.eo = eo;
		this.epoch = epoch;
		this.incl = incl;
		this.meanan = meanan;
		this.meanmo = meanmo;
		this.name = name;
		this.nddot6 = nddot6;
		this.omega = omega;
		this.raan = raan;
		this.refepoch = refepoch;
		this.setnum = setnum;
		this.xincl = xincl;
		this.mo = mo;
		this.xndt2o = xndt2o;
		this.xno = xno;
		this.xnodeo = xnodeo;
		this.deepspace = deepspace;
		this.year = year;
		this.createddate = createddate;
		this.orbitnum = orbitnum;
	}

	/**
	 * @return the catnum
	 */
	public final long getCatnum() {
		return catnum;
	}

	/**
	 * @param catnum the catnum to set
	 */
	public final void setCatnum(int catnum) {
		this.catnum = catnum;
	}

	/**
	 * @return the argper
	 */
	public final double getArgper() {
		return argper;
	}

	/**
	 * @param argper the argper to set
	 */
	public final void setArgper(double argper) {
		this.argper = argper;
	}

	/**
	 * @return the bstar
	 */
	public final double getBstar() {
		return bstar;
	}

	/**
	 * @param bstar the bstar to set
	 */
	public final void setBstar(double bstar) {
		this.bstar = bstar;
	}

	/**
	 * @return the drag
	 */
	public final double getDrag() {
		return drag;
	}

	/**
	 * @param drag the drag to set
	 */
	public final void setDrag(double drag) {
		this.drag = drag;
	}

	/**
	 * @return the eccn
	 */
	public final double getEccn() {
		return eccn;
	}

	/**
	 * @param eccn the eccn to set
	 */
	public final void setEccn(double eccn) {
		this.eccn = eccn;
	}

	/**
	 * @return the eo
	 */
	public final double getEo() {
		return eo;
	}

	/**
	 * @param eo the eo to set
	 */
	public final void setEo(double eo) {
		this.eo = eo;
	}

	/**
	 * @return the epoch
	 */
	public final double getEpoch() {
		return epoch;
	}

	/**
	 * @param epoch the epoch to set
	 */
	public final void setEpoch(double epoch) {
		this.epoch = epoch;
	}

	/**
	 * @return the incl
	 */
	public final double getIncl() {
		return incl;
	}

	/**
	 * @param incl the incl to set
	 */
	public final void setIncl(double incl) {
		this.incl = incl;
	}

	/**
	 * @return the meanan
	 */
	public final double getMeanan() {
		return meanan;
	}

	/**
	 * @param meanan the meanan to set
	 */
	public final void setMeanan(double meanan) {
		this.meanan = meanan;
	}

	/**
	 * @return the meanmo
	 */
	public final double getMeanmo() {
		return meanmo;
	}

	/**
	 * @param meanmo the meanmo to set
	 */
	public final void setMeanmo(double meanmo) {
		this.meanmo = meanmo;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nddot6
	 */
	public final double getNddot6() {
		return nddot6;
	}

	/**
	 * @param nddot6 the nddot6 to set
	 */
	public final void setNddot6(double nddot6) {
		this.nddot6 = nddot6;
	}

	/**
	 * @return the omega
	 */
	public final double getOmega() {
		return omega;
	}

	/**
	 * @param omega the omega to set
	 */
	public final void setOmega(double omega) {
		this.omega = omega;
	}

	/**
	 * @return the raan
	 */
	public final double getRaan() {
		return raan;
	}

	/**
	 * @param raan the raan to set
	 */
	public final void setRaan(double raan) {
		this.raan = raan;
	}

	/**
	 * @return the refepoch
	 */
	public final double getRefepoch() {
		return refepoch;
	}

	/**
	 * @param refepoch the refepoch to set
	 */
	public final void setRefepoch(double refepoch) {
		this.refepoch = refepoch;
	}

	/**
	 * @return the setnum
	 */
	public final int getSetnum() {
		return setnum;
	}

	/**
	 * @param setnum the setnum to set
	 */
	public final void setSetnum(int setnum) {
		this.setnum = setnum;
	}

	/**
	 * @return the xincl
	 */
	public final double getXincl() {
		return xincl;
	}

	/**
	 * @param xincl the xincl to set
	 */
	public final void setXincl(double xincl) {
		this.xincl = xincl;
	}

	/**
	 * @return the mo
	 */
	public final double getMo() {
		return mo;
	}

	/**
	 * @param mo the mo to set
	 */
	public final void setMo(double mo) {
		this.mo = mo;
	}

	/**
	 * @return the xndt2o
	 */
	public final double getXndt2o() {
		return xndt2o;
	}

	/**
	 * @param xndt2o the xndt2o to set
	 */
	public final void setXndt2o(double xndt2o) {
		this.xndt2o = xndt2o;
	}

	/**
	 * @return the xno
	 */
	public final double getXno() {
		return xno;
	}

	/**
	 * @param xno the xno to set
	 */
	public final void setXno(double xno) {
		this.xno = xno;
	}

	/**
	 * @return the xnodeo
	 */
	public final double getXnodeo() {
		return xnodeo;
	}

	/**
	 * @param xnodeo the xnodeo to set
	 */
	public final void setXnodeo(double xnodeo) {
		this.xnodeo = xnodeo;
	}

	/**
	 * @return the deepspace
	 */
	public final boolean isDeepspace() {
		return deepspace;
	}

	/**
	 * @param deepspace the deepspace to set
	 */
	public final void setDeepspace(boolean deepspace) {
		this.deepspace = deepspace;
	}

	/**
	 * @return the year
	 */
	public final int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public final void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the createddate
	 */
	public final Date getCreateddate() {
		return createddate;
	}

	/**
	 * @param createddate the createddate to set
	 */
	public final void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	/**
	 * @return the orbitnum
	 */
	public final long getOrbitnum() {
		return orbitnum;
	}

	/**
	 * @param orbitnum the orbitnum to set
	 */
	public final void setOrbitnum(long orbitnum) {
		this.orbitnum = orbitnum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(argper);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(bstar);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (catnum ^ (catnum >>> 32));
		result = prime * result
				+ ((createddate == null) ? 0 : createddate.hashCode());
		result = prime * result + (deepspace ? 1231 : 1237);
		temp = Double.doubleToLongBits(drag);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(eccn);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(eo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(epoch);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(incl);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(meanan);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(meanmo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(nddot6);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(omega);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (orbitnum ^ (orbitnum >>> 32));
		temp = Double.doubleToLongBits(raan);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(refepoch);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + setnum;
		temp = Double.doubleToLongBits(xincl);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xndt2o);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xno);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xnodeo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + year;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TleEntity other = (TleEntity) obj;
		if (Double.doubleToLongBits(argper) != Double
				.doubleToLongBits(other.argper)) {
			return false;
		}
		if (Double.doubleToLongBits(bstar) != Double
				.doubleToLongBits(other.bstar)) {
			return false;
		}
		if (catnum != other.catnum) {
			return false;
		}
		if (createddate == null) {
			if (other.createddate != null) {
				return false;
			}
		} else if (!createddate.equals(other.createddate)) {
			return false;
		}
		if (deepspace != other.deepspace) {
			return false;
		}
		if (Double.doubleToLongBits(drag) != Double
				.doubleToLongBits(other.drag)) {
			return false;
		}
		if (Double.doubleToLongBits(eccn) != Double
				.doubleToLongBits(other.eccn)) {
			return false;
		}
		if (Double.doubleToLongBits(eo) != Double.doubleToLongBits(other.eo)) {
			return false;
		}
		if (Double.doubleToLongBits(epoch) != Double
				.doubleToLongBits(other.epoch)) {
			return false;
		}
		if (Double.doubleToLongBits(incl) != Double
				.doubleToLongBits(other.incl)) {
			return false;
		}
		if (Double.doubleToLongBits(meanan) != Double
				.doubleToLongBits(other.meanan)) {
			return false;
		}
		if (Double.doubleToLongBits(meanmo) != Double
				.doubleToLongBits(other.meanmo)) {
			return false;
		}
		if (Double.doubleToLongBits(mo) != Double.doubleToLongBits(other.mo)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (Double.doubleToLongBits(nddot6) != Double
				.doubleToLongBits(other.nddot6)) {
			return false;
		}
		if (Double.doubleToLongBits(omega) != Double
				.doubleToLongBits(other.omega)) {
			return false;
		}
		if (orbitnum != other.orbitnum) {
			return false;
		}
		if (Double.doubleToLongBits(raan) != Double
				.doubleToLongBits(other.raan)) {
			return false;
		}
		if (Double.doubleToLongBits(refepoch) != Double
				.doubleToLongBits(other.refepoch)) {
			return false;
		}
		if (setnum != other.setnum) {
			return false;
		}
		if (Double.doubleToLongBits(xincl) != Double
				.doubleToLongBits(other.xincl)) {
			return false;
		}
		if (Double.doubleToLongBits(xndt2o) != Double
				.doubleToLongBits(other.xndt2o)) {
			return false;
		}
		if (Double.doubleToLongBits(xno) != Double.doubleToLongBits(other.xno)) {
			return false;
		}
		if (Double.doubleToLongBits(xnodeo) != Double
				.doubleToLongBits(other.xnodeo)) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}
}
