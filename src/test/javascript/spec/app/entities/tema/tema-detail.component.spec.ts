/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HealthSecurityTestModule } from '../../../test.module';
import { TemaDetailComponent } from 'app/entities/tema/tema-detail.component';
import { Tema } from 'app/shared/model/tema.model';

describe('Component Tests', () => {
    describe('Tema Management Detail Component', () => {
        let comp: TemaDetailComponent;
        let fixture: ComponentFixture<TemaDetailComponent>;
        const route = ({ data: of({ tema: new Tema(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityTestModule],
                declarations: [TemaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TemaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TemaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tema).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
