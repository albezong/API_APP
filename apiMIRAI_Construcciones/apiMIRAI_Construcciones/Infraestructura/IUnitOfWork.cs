using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace apiMIRAI_Construcciones.Infraestructura
{
    interface IUnitOfWork : IDisposable
    {
        IRepository<QrEquipos> QrEquipos { get; }
        //IRepository<Usuario> Usuarios { get; }
        int Complete();
        bool GuardarCambios(int id, byte[] QR);
    }
}
