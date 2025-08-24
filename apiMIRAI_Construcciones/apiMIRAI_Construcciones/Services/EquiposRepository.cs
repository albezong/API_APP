using APIMIRAI_Construcciones.Data;
using APIMIRAI_Construcciones.Infraestructura;
using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Web;

namespace APIMIRAI_Construcciones.Services
{
	public class EquiposRepository : IRepository<QrEquipos>
    {
        private readonly PruebaAlmacenTAEPIEntities1 _tentities;
        public EquiposRepository()
        {
            _tentities = new PruebaAlmacenTAEPIEntities1();
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
            return _tentities.QrEquipos.Find(id);
        }

        public void Update(QrEquipos qrEquiposEntity)
        {
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
