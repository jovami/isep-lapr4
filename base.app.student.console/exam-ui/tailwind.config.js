/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{html,svelte,js,ts}"
    ],
    theme: {
        extend: {},
    },
    plugins: [require('@catppuccin/tailwindcss')({
        prefix: 'ctp',
        defaultFlavour: 'mocha',
    })],
}

