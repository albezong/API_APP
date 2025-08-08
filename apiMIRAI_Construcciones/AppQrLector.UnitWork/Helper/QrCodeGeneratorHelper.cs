using QRCoder;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppQrLector.UnitWork.Helper
{
    class QrCodeGeneratorHelper
    {
        public static byte[] GenerateQRCode(string data)
        {
            using (var qrGenerator = new QRCodeGenerator())
            using (var qrData = qrGenerator.CreateQrCode(data, QRCodeGenerator.ECCLevel.Q))
            using (var qrCode = new QRCode(qrData))
            using (Bitmap bitmap = qrCode.GetGraphic(20))
            using (var ms = new MemoryStream())
            {
                bitmap.Save(ms, System.Drawing.Imaging.ImageFormat.Png);
                return ms.ToArray();
            }
        }
    }
}