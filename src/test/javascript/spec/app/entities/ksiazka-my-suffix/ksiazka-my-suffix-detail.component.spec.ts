/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { KsiazkaMySuffixDetailComponent } from 'app/entities/ksiazka-my-suffix/ksiazka-my-suffix-detail.component';
import { KsiazkaMySuffix } from 'app/shared/model/ksiazka-my-suffix.model';

describe('Component Tests', () => {
    describe('KsiazkaMySuffix Management Detail Component', () => {
        let comp: KsiazkaMySuffixDetailComponent;
        let fixture: ComponentFixture<KsiazkaMySuffixDetailComponent>;
        const route = ({ data: of({ ksiazka: new KsiazkaMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [KsiazkaMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(KsiazkaMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KsiazkaMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ksiazka).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
