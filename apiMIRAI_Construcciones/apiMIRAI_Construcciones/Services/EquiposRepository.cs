using apiMIRAI_Construcciones.Data;
using apiMIRAI_Construcciones.Data.QRDB;
using apiMIRAI_Construcciones.Infraestructura;
using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Services
{
	public class EquiposRepository : IRepository<QrEquipos>
    {
        private readonly AlmacenTAEPIEntities _tentities;
        public EquiposRepository()
        {
            _tentities = new AlmacenTAEPIEntities();
        }

        public void Add(QrEquipos entity)
        {
            throw new NotImplementedException();
        }

        public void Delete(int entity)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<QrEquipos> GetAll()
        {
            return _tentities.QrEquipos.ToList();
        }

        public QrEquipos GetById(int id)
        {
            //throw new NotImplementedException();
            return _tentities.QrEquipos.Find(id);
        }

        public void Update(QrEquipos qrEquiposEntity)
        {
            //throw new NotImplementedException();
            _tentities.QrEquipos.Add(qrEquiposEntity);

            try
            {
                _tentities.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (usuariosExists(qrEquiposEntity.idQrEquipos))
                {
                    return;
                }
                else
                {
                    throw;
                }
            }
        }
        private bool usuariosExists(int id)
        {
            return _tentities.Equipos.Count(e => e.idEquipos == id) > 0;
        }
    }
}
