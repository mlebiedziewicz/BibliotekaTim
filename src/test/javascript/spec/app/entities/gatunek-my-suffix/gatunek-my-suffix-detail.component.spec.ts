/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { GatunekMySuffixDetailComponent } from 'app/entities/gatunek-my-suffix/gatunek-my-suffix-detail.component';
import { GatunekMySuffix } from 'app/shared/model/gatunek-my-suffix.model';

describe('Component Tests', () => {
    describe('GatunekMySuffix Management Detail Component', () => {
        let comp: GatunekMySuffixDetailComponent;
        let fixture: ComponentFixture<GatunekMySuffixDetailComponent>;
        const route = ({ data: of({ gatunek: new GatunekMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [GatunekMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GatunekMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GatunekMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.gatunek).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
