/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MicroTestModule } from '../../../test.module';
import { InfoDefinitionComponent } from '../../../../../../main/webapp/app/entities/info-definition/info-definition.component';
import { InfoDefinitionService } from '../../../../../../main/webapp/app/entities/info-definition/info-definition.service';
import { InfoDefinition } from '../../../../../../main/webapp/app/entities/info-definition/info-definition.model';

describe('Component Tests', () => {

    describe('InfoDefinition Management Component', () => {
        let comp: InfoDefinitionComponent;
        let fixture: ComponentFixture<InfoDefinitionComponent>;
        let service: InfoDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [InfoDefinitionComponent],
                providers: [
                    InfoDefinitionService
                ]
            })
            .overrideTemplate(InfoDefinitionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InfoDefinitionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InfoDefinitionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InfoDefinition(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.infoDefinitions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
