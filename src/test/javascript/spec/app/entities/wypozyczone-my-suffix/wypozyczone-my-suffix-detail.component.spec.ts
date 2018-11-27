/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { WypozyczoneMySuffixDetailComponent } from 'app/entities/wypozyczone-my-suffix/wypozyczone-my-suffix-detail.component';
import { WypozyczoneMySuffix } from 'app/shared/model/wypozyczone-my-suffix.model';

describe('Component Tests', () => {
    describe('WypozyczoneMySuffix Management Detail Component', () => {
        let comp: WypozyczoneMySuffixDetailComponent;
        let fixture: ComponentFixture<WypozyczoneMySuffixDetailComponent>;
        const route = ({ data: of({ wypozyczone: new WypozyczoneMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [WypozyczoneMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WypozyczoneMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WypozyczoneMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.wypozyczone).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
