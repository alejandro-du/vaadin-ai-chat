// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

import '@vaadin/vaadin-charts/theme/vaadin-chart-default-theme';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
<custom-style>
  <style>

html {
  --lumo-font-family: Consolas, "Andale Mono WT", "Andale Mono", "Lucida Console", "Lucida Sans Typewriter", "DejaVu Sans Mono", "Bitstream Vera Sans Mono", "Liberation Mono", "Nimbus Mono L", Monaco, "Courier New", Courier, monospace;
  --lumo-line-height-m: 1.4;
  --lumo-line-height-s: 1.2;
  --lumo-line-height-xs: 1.1;
  --lumo-border-radius: 0.5em;
}

[theme~="dark"] {
  --lumo-base-color: hsl(199, 18%, 10%);
  --lumo-primary-contrast-color: #9498a8;
  --lumo-primary-text-color: rgb(175, 199, 223);
  --lumo-primary-color-50pct: rgba(102, 161, 219, 0.5);
  --lumo-primary-color-10pct: rgba(102, 161, 219, 0.1);
  --lumo-primary-color: hsl(210, 62%, 63%);
}

  </style>
</custom-style>
`;

document.head.appendChild($_documentContainer.content);
