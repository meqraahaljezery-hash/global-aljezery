/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        gold: {
          light: '#F5D77F',
          DEFAULT: '#D4AF37',
          dark: '#997F24',
        },
        skyBlue: {
          50: '#F0F9FF',
          100: '#E0F2FE',
          200: '#BAE6FD',
          300: '#7DD3FC',
          400: '#38BDF8',
          500: '#0EA5E9',
          600: '#0284C7',
          700: '#0369A1',
        },
        islamic: {
          midnight: '#0A1530',
          surfaceDark: '#122044',
          cardDark: '#182B59',
          borderDark: '#233B76',
        }
      },
      fontFamily: {
        arabic: ['Cairo', 'Traditional Arabic', 'sans-serif'],
        amharic: ['Noto Sans Ethiopic', 'sans-serif'],
        english: ['Inter', 'system-ui', 'sans-serif'],
      }
    },
  },
  plugins: [],
}
