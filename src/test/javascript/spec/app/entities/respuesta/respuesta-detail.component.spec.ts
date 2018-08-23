/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HealthSecurityWebTestModule } from '../../../test.module';
import { RespuestaDetailComponent } from 'app/entities/respuesta/respuesta-detail.component';
import { Respuesta } from 'app/shared/model/respuesta.model';

describe('Component Tests', () => {
    describe('Respuesta Management Detail Component', () => {
        let comp: RespuestaDetailComponent;
        let fixture: ComponentFixture<RespuestaDetailComponent>;
        const route = ({ data: of({ respuesta: new Respuesta(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityWebTestModule],
                declarations: [RespuestaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RespuestaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RespuestaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.respuesta).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
