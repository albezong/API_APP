using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using api



namespace AppQrLector.UnitWork
{/*
    public class UnitOfWork : IUnitOfWork
    {
        private readonly QrBDModel1 _context;
        public IRepository<Usuario> Usuarios { get; private set; }

        //public IRepository<Usuario> Usuarios => throw new NotImplementedException();

        public UnitOfWork()
        {
            this._context = new QrBDModel1();
            Usuarios = new Repository.Contract.UsuarioRepository();
        }

        public bool GuardarCambios(int id, byte[] QR)
        {
            try
            {
                var idQR = _context.Usuario.Find(id);
                if (idQR == null) return false;

                idQR.ProviderKey = Convert.ToBase64String(QR);

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
    */

}
