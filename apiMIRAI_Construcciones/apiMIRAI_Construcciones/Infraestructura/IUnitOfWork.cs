using APIMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace APIMIRAI_Construcciones.Infraestructura
{
    interface IUnitOfWork : IDisposable
    {
        IRepository<QrEquipos> QrEquipos { get; }
        int Complete();
        bool GuardarCambios(int id, byte[] QR);
    }
}
