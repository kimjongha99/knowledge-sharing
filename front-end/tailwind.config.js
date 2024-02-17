module.exports = {
  purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      colors: { // Use 'colors' instead of 'color'
        primary: '#93c5fd', // This color will now be recognized as the primary color
      }
    },
  },
  variants: {
    extend: {},
  },
  plugins: [],
};