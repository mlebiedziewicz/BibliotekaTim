/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { WydawnictwoMySuffixDetailComponent } from 'app/entities/wydawnictwo-my-suffix/wydawnictwo-my-suffix-detail.component';
import { WydawnictwoMySuffix } from 'app/shared/model/wydawnictwo-my-suffix.model';

describe('Component Tests', () => {
    describe('WydawnictwoMySuffix Management Detail Component', () => {
        let comp: WydawnictwoMySuffixDetailComponent;
        let fixture: ComponentFixture<WydawnictwoMySuffixDetailComponent>;
        const route = ({ data: of({ wydawnictwo: new WydawnictwoMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [WydawnictwoMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WydawnictwoMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WydawnictwoMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.wydawnictwo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
