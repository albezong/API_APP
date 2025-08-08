using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using apiMIRAI_Construcciones.Infraestructura;
using apiMIRAI_Construcciones.Data.QRDB;

namespace apiMIRAI_Construcciones.Services
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly AlmacenTAEPIEntities _context;
        public IRepository<QrEquipos> QrEquipos { get; private set; }

        public UnitOfWork()
        {
            this._context = new AlmacenTAEPIEntities();
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


            /*
            if (QR != null)
            {

                 = Convert.ToBase64String(QR);
                _context.SaveChanges();
                return true;
            }
            else { return false;  }*/
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