import { defineConfig } from 'vite';

export default defineConfig({
  optimizeDeps: {
    exclude: [
      '@spartan-ng/ui-alertdialog-helm',
      '@spartan-ng/ui-button-helm',
      '@spartan-ng/ui-card-helm',
      '@spartan-ng/ui-input-helm',
      '@spartan-ng/ui-label-helm',
      '@spartan-ng/ui-menu-helm',
      '@spartan-ng/ui-popover-helm',
      '@spartan-ng/ui-select-helm'
  ]
  }
});
