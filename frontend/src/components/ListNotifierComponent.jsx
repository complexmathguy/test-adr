import React, { Component } from 'react'
import NotifierService from '../services/NotifierService'

class ListNotifierComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                notifiers: []
        }
        this.addNotifier = this.addNotifier.bind(this);
        this.editNotifier = this.editNotifier.bind(this);
        this.deleteNotifier = this.deleteNotifier.bind(this);
    }

    deleteNotifier(id){
        NotifierService.deleteNotifier(id).then( res => {
            this.setState({notifiers: this.state.notifiers.filter(notifier => notifier.notifierId !== id)});
        });
    }
    viewNotifier(id){
        this.props.history.push(`/view-notifier/${id}`);
    }
    editNotifier(id){
        this.props.history.push(`/add-notifier/${id}`);
    }

    componentDidMount(){
        NotifierService.getNotifiers().then((res) => {
            this.setState({ notifiers: res.data});
        });
    }

    addNotifier(){
        this.props.history.push('/add-notifier/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Notifier List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addNotifier}> Add Notifier</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.notifiers.map(
                                        notifier => 
                                        <tr key = {notifier.notifierId}>
                                             <td>
                                                 <button onClick={ () => this.editNotifier(notifier.notifierId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteNotifier(notifier.notifierId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewNotifier(notifier.notifierId)} className="btn btn-info btn-sm">View </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ListNotifierComponent
