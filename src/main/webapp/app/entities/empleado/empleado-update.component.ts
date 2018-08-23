import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEmpleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from './empleado.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-empleado-update',
    templateUrl: './empleado-update.component.html'
})
export class EmpleadoUpdateComponent implements OnInit {
    private _empleado: IEmpleado;
    isSaving: boolean;

    users: IUser[];
    nacimientoDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private empleadoService: EmpleadoService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ empleado }) => {
            this.empleado = empleado;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.empleado.id !== undefined) {
            this.subscribeToSaveResponse(this.empleadoService.update(this.empleado));
        } else {
            this.subscribeToSaveResponse(this.empleadoService.create(this.empleado));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleado>>) {
        result.subscribe((res: HttpResponse<IEmpleado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get empleado() {
        return this._empleado;
    }

    set empleado(empleado: IEmpleado) {
        this._empleado = empleado;
    }
}
