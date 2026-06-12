package util;

public class ValidarSenhaUtil {

		public static String validarSenha (String senha) {
			String requisitoFaltante = "";
			boolean maiuscula = false;
			boolean minuscula = false;
			boolean numero = false;
			boolean caractere = false;
			boolean tamanho = false;
			String especiais = "@!$#&*";
			
			if (senha.length() >= 6) {
				tamanho = true;
			} else {
				requisitoFaltante += "A senha deve ter no mínimo 6 caracteres.\n";
			}

						
			for (int i = 0; i < senha.length(); i++) {
				if (Character.isDigit(senha.charAt(i))) {
					numero = true;
				} else if (Character.isLowerCase(senha.charAt(i))) {
					minuscula = true;
				} else if (Character.isUpperCase(senha.charAt(i))) {
					maiuscula = true;
				} else if (especiais.contains(String.valueOf(senha.charAt(i)))) {
					caractere = true;
				}
			}
			
			if (maiuscula == false) {
				requisitoFaltante += "A senha deve conter uma letra maiúscula.\n";
			}
			
			if (minuscula == false) {
				requisitoFaltante += "A senha deve conter uma letra minúscula.\n";
			}
			
			if (numero == false) {
				requisitoFaltante += "A senha deve conter um número.\n";
			}
			
			if (caractere == false) {
				requisitoFaltante += "A senha deve conter ao menos um caractere especial (@!$#&*).";
			}
			
			
			return requisitoFaltante;
			
		}
}
