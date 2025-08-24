using APIMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIMIRAI_Construcciones.Infraestructura;
using APIMIRAI_Construcciones.Services;

namespace APIMIRAI_Construcciones.Services
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly PruebaAlmacenTAEPIEntities1 _context;
        public IRepository<QrEquipos> QrEquipos { get; private set; }

        public UnitOfWork()
        {
            this._context = new PruebaAlmacenTAEPIEntities1();
            QrEquipos = new EquiposRepository();
        }

        public bool GuardarCambios(int id, byte[] QR)
        {
            try
            {
                var idQR = _context.QrEquipos.Find(id);
                if (idQR == null) return false;

                idQR.claveQR = Convert.ToBase64String(QR);

                _context.SaveChanges();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public int Complete()
        {
            return this._context.SaveChanges();
        }
        public void Disponse() => _context.Dispose();

        public void Dispose()
        {
            throw new NotImplementedException();
        }    
    }
}