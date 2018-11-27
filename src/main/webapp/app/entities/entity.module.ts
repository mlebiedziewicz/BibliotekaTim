import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BibliotekaTimKsiazkaMySuffixModule } from './ksiazka-my-suffix/ksiazka-my-suffix.module';
import { BibliotekaTimAutorMySuffixModule } from './autor-my-suffix/autor-my-suffix.module';
import { BibliotekaTimGatunekMySuffixModule } from './gatunek-my-suffix/gatunek-my-suffix.module';
import { BibliotekaTimWydawnictwoMySuffixModule } from './wydawnictwo-my-suffix/wydawnictwo-my-suffix.module';
import { BibliotekaTimWypozyczoneMySuffixModule } from './wypozyczone-my-suffix/wypozyczone-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BibliotekaTimKsiazkaMySuffixModule,
        BibliotekaTimAutorMySuffixModule,
        BibliotekaTimGatunekMySuffixModule,
        BibliotekaTimWydawnictwoMySuffixModule,
        BibliotekaTimWypozyczoneMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BibliotekaTimEntityModule {}
