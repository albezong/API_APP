using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppQrLector.UnitWork.Contract
{
    interface IUnitOfWork : IDisposable
    {
        IRepository<Usuario> Usuarios { get; }
        //IRepository<Usuario> Usuarios { get; }
        int Complete();
        bool GuardarCambios(int id, byte[] QR);
    }
}
