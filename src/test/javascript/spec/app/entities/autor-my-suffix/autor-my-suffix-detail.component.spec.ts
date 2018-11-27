/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { AutorMySuffixDetailComponent } from 'app/entities/autor-my-suffix/autor-my-suffix-detail.component';
import { AutorMySuffix } from 'app/shared/model/autor-my-suffix.model';

describe('Component Tests', () => {
    describe('AutorMySuffix Management Detail Component', () => {
        let comp: AutorMySuffixDetailComponent;
        let fixture: ComponentFixture<AutorMySuffixDetailComponent>;
        const route = ({ data: of({ autor: new AutorMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [AutorMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AutorMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AutorMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.autor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
